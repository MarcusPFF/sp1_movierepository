package app.daos;

import app.entities.Director;
import app.entities.Movie;
import app.entities.MovieDirectorRelations;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class MovieDirectorRelationsDAO implements IDAO<MovieDirectorRelations, Integer> {
    private final EntityManagerFactory emf;
    private final List<MovieDirectorRelations> relations;

    public MovieDirectorRelationsDAO(EntityManagerFactory emf) {
        this.emf = emf;
        this.relations = new ArrayList<>();
    }

    @Override
    public List<MovieDirectorRelations> create(List<MovieDirectorRelations> relationsList) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            List<MovieDirectorRelations> managedRelations = new ArrayList<>();

            for (MovieDirectorRelations rel : relationsList) {
                Movie managedMovie = rel.getMovie() != null ? em.merge(rel.getMovie()) : null;
                Director managedDirector = rel.getDirector() != null ? em.merge(rel.getDirector()) : null;

                rel.setMovie(managedMovie);
                rel.setDirector(managedDirector);

                MovieDirectorRelations managedRel = em.merge(rel);
                managedRelations.add(managedRel);
                this.relations.add(managedRel);
            }

            em.getTransaction().commit();
            return managedRelations;

        } catch (Exception e) {
            throw new RuntimeException("Could not persist movie-director relations", e);
        }
    }

    @Override
    public MovieDirectorRelations create(MovieDirectorRelations relation) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            if (relation.getMovie() != null && relation.getMovie().getId() != null) {
                Movie movieRef = em.find(Movie.class, relation.getMovie().getId());
                relation.setMovie(movieRef);
                if (movieRef != null) movieRef.getMovieDirectorRelations().add(relation);
            }
            if (relation.getDirector() != null && relation.getDirector().getId() != null) {
                Director directorRef = em.find(Director.class, relation.getDirector().getId());
                relation.setDirector(directorRef);
                if (directorRef != null) directorRef.getMovieDirectorRelations().add(relation);
            }
            em.persist(relation);
            em.getTransaction().commit();
            this.relations.add(relation);
            return relation;
        } catch (Exception e) {
            throw new RuntimeException("Could not persist movie-director relation", e);
        }
    }

    @Override
    public MovieDirectorRelations getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(MovieDirectorRelations.class, id);
        }
    }

    @Override
    public List<MovieDirectorRelations> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT mdr FROM MovieDirectorRelations mdr", MovieDirectorRelations.class)
                    .getResultList();
        }
    }

    @Override
    public MovieDirectorRelations update(MovieDirectorRelations relation) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            MovieDirectorRelations existing = em.find(MovieDirectorRelations.class, relation.getId());
            if (existing == null) {
                em.getTransaction().rollback();
                return null;
            }

            existing.setJobTitle(relation.getJobTitle());

            if (relation.getMovie() != null && relation.getMovie().getId() != null) {
                if (existing.getMovie() == null || !existing.getMovie().getId().equals(relation.getMovie().getId())) {
                    if (existing.getMovie() != null) existing.getMovie().getMovieDirectorRelations().remove(existing);
                    Movie newMovie = em.find(Movie.class, relation.getMovie().getId());
                    existing.setMovie(newMovie);
                    if (newMovie != null) newMovie.getMovieDirectorRelations().add(existing);
                }
            }

            if (relation.getDirector() != null && relation.getDirector().getId() != null) {
                if (existing.getDirector() == null || !existing.getDirector().getId().equals(relation.getDirector().getId())) {
                    if (existing.getDirector() != null)
                        existing.getDirector().getMovieDirectorRelations().remove(existing);
                    Director newDirector = em.find(Director.class, relation.getDirector().getId());
                    existing.setDirector(newDirector);
                    if (newDirector != null) newDirector.getMovieDirectorRelations().add(existing);
                }
            }

            MovieDirectorRelations merged = em.merge(existing);
            em.getTransaction().commit();
            this.relations.removeIf(r -> r.getId().equals(merged.getId()));
            this.relations.add(merged);
            return merged;
        } catch (Exception e) {
            throw new RuntimeException("Could not update movie-director relation", e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            MovieDirectorRelations rel = em.find(MovieDirectorRelations.class, id);
            if (rel == null) {
                em.getTransaction().rollback();
                return false;
            }
            if (rel.getMovie() != null) rel.getMovie().getMovieDirectorRelations().remove(rel);
            if (rel.getDirector() != null) rel.getDirector().getMovieDirectorRelations().remove(rel);
            em.remove(rel);
            em.getTransaction().commit();
            this.relations.removeIf(r -> r.getId().equals(id));
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Could not delete movie-director relation", e);
        }
    }
}
