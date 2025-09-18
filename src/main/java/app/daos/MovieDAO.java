package app.daos;

import app.entities.Genre;
import app.entities.Movie;
import app.utils.GenerateNextId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieDAO implements IDAO<Movie, Integer> {
    private final EntityManagerFactory emf;
    private final List<Movie> movies;
    private final GenerateNextId gni;

    public MovieDAO(EntityManagerFactory emf) {
        this.emf = emf;
        movies = new ArrayList<>();
        gni = new GenerateNextId(emf);
    }

    public List<Movie> create(List<Movie> movies) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        for (Movie movie : movies) {
            Set<Genre> managedGenres = movie.getGenres().stream().map(g -> ensureManagedGenre(em, g)).collect(Collectors.toSet());
            movie.setGenres(managedGenres);
            em.merge(movie);
        }

        em.getTransaction().commit();
        em.close();
        return movies;
    }

    @Override
    public Movie create(Movie movie) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            //TODO test om denne metode virker nu med det nye kode neden under.
            movie.setId(gni.generateNextId(Movie.class));
            em.merge(movie);
            em.getTransaction().commit();
            this.movies.add(movie);
            return movie;
        } catch (Exception e) {
            throw new RuntimeException("Could not persist movie", e);
        }
    }

    @Override
    public Movie getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Movie.class, id);
        }
    }

    @Override
    public List<Movie> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
        }
    }


    @Override
    public Movie update(Movie movie) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Movie existingMovie = em.find(Movie.class, movie.getId());
            if (existingMovie == null) {
                em.getTransaction().rollback();
                return null;
            }

            existingMovie.setOriginalTitle(movie.getOriginalTitle());
            existingMovie.setReleaseDate(movie.getReleaseDate());

            Movie mergedMovie = em.merge(existingMovie);
            em.getTransaction().commit();
            return mergedMovie;
        } catch (Exception e) {
            throw new RuntimeException("Could not update movie", e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Movie movie = em.find(Movie.class, id);
            if (movie == null) {
                em.getTransaction().rollback();
                return false;
            }
            em.remove(movie);
            em.getTransaction().commit();
            for (int i = 0; i < movies.size(); i++) {
                if (movies.get(i).getId().equals(id)) {
                    movies.remove(i);
                    break;
                }
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Could not delete movie", e);
        }
    }

    private Genre ensureManagedGenre(EntityManager em, Genre genre) {
        if (genre.getId() != null) {
            Genre managed = em.find(Genre.class, genre.getId());
            if (managed != null) {
                return managed;
            }
        }

        Genre newGenre = new Genre();
        newGenre.setGenreName(genre.getGenreName());
        em.persist(newGenre);
        return newGenre;
    }

}
