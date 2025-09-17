package app.daos;

import app.entities.Actor;
import app.entities.Movie;
import app.entities.MovieActorRelations;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class MovieActorRelationsDAO implements IDAO<MovieActorRelations, Integer> {
    private final EntityManagerFactory emf;
    private final List<MovieActorRelations> relations;

    public MovieActorRelationsDAO(EntityManagerFactory emf) {
        this.emf = emf;
        this.relations = new ArrayList<>();
    }

    @Override
    public List<MovieActorRelations> create(List<MovieActorRelations> relationsList) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            List<MovieActorRelations> managedRelations = new ArrayList<>();
            for (MovieActorRelations rel : relationsList) {
                Movie managedMovie = rel.getMovie() != null && rel.getMovie().getId() != null
                        ? em.find(Movie.class, rel.getMovie().getId())
                        : null;
                Actor managedActor = rel.getActor() != null && rel.getActor().getId() != null
                        ? em.find(Actor.class, rel.getActor().getId())
                        : null;
                rel.setMovie(managedMovie);
                rel.setActor(managedActor);
                em.persist(rel);
                managedRelations.add(rel);
                this.relations.add(rel);
            }
            em.getTransaction().commit();
            return managedRelations;
        } catch (Exception e) {
            throw new RuntimeException("Could not persist movie-actor relations", e);
        }
    }

//    @Override
//    public List<MovieActorRelations> create(List<MovieActorRelations> relationsList) {
//        try (EntityManager em = emf.createEntityManager()) {
//            em.getTransaction().begin();
//
//            List<MovieActorRelations> managedRelations = new ArrayList<>();
//
//            for (MovieActorRelations rel : relationsList) {
//                Movie managedMovie = rel.getMovie() != null ? em.merge(rel.getMovie()) : null;
//                Actor managedActor = rel.getActor() != null ? em.merge(rel.getActor()) : null;
//
//                rel.setMovie(managedMovie);
//                rel.setActor(managedActor);
//
//                MovieActorRelations managedRel = em.merge(rel);
//                managedRelations.add(managedRel);
//                this.relations.add(managedRel);
//            }
//
//            em.getTransaction().commit();
//            return managedRelations;
//
//        } catch (Exception e) {
//            throw new RuntimeException("Could not persist movie-actor relations", e);
//        }
//    }

    @Override
    public MovieActorRelations create(MovieActorRelations relation) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            if (relation.getMovie() != null && relation.getMovie().getId() != null) {
                Movie movieRef = em.find(Movie.class, relation.getMovie().getId());
                relation.setMovie(movieRef);
                if (movieRef != null) movieRef.getMovieActorRelations().add(relation);
            }
            if (relation.getActor() != null && relation.getActor().getId() != null) {
                Actor actorRef = em.find(Actor.class, relation.getActor().getId());
                relation.setActor(actorRef);
                if (actorRef != null) actorRef.getMovieActorRelations().add(relation);
            }
            em.persist(relation);
            em.getTransaction().commit();
            this.relations.add(relation);
            return relation;
        } catch (Exception e) {
            throw new RuntimeException("Could not persist movie-actor relation", e);
        }
    }

    @Override
    public MovieActorRelations getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(MovieActorRelations.class, id);
        }
    }

    @Override
    public List<MovieActorRelations> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT mar FROM MovieActorRelations mar", MovieActorRelations.class).getResultList();
        }
    }

    @Override
    public MovieActorRelations update(MovieActorRelations relation) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            MovieActorRelations existing = em.find(MovieActorRelations.class, relation.getId());
            if (existing == null) {
                em.getTransaction().rollback();
                return null;
            }

            existing.setCharacterName(relation.getCharacterName());
            existing.setCastId(relation.getCastId());

            if (relation.getMovie() != null && relation.getMovie().getId() != null) {
                if (existing.getMovie() == null || !existing.getMovie().getId().equals(relation.getMovie().getId())) {

                    if (existing.getMovie() != null) {
                        existing.getMovie().getMovieActorRelations().remove(existing);
                    }
                    Movie newMovie = em.find(Movie.class, relation.getMovie().getId());
                    existing.setMovie(newMovie);
                    if (newMovie != null) newMovie.getMovieActorRelations().add(existing);
                }
            }

            if (relation.getActor() != null && relation.getActor().getId() != null) {
                if (existing.getActor() == null || !existing.getActor().getId().equals(relation.getActor().getId())) {
                    if (existing.getActor() != null) {
                        existing.getActor().getMovieActorRelations().remove(existing);
                    }
                    Actor newActor = em.find(Actor.class, relation.getActor().getId());
                    existing.setActor(newActor);
                    if (newActor != null) newActor.getMovieActorRelations().add(existing);
                }
            }

            MovieActorRelations merged = em.merge(existing);
            em.getTransaction().commit();
            this.relations.removeIf(r -> r.getId().equals(merged.getId()));
            this.relations.add(merged);
            return merged;
        } catch (Exception e) {
            throw new RuntimeException("Could not update movie-actor relation", e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            MovieActorRelations rel = em.find(MovieActorRelations.class, id);
            if (rel == null) {
                em.getTransaction().rollback();
                return false;
            }
            if (rel.getMovie() != null) {
                rel.getMovie().getMovieActorRelations().remove(rel);
            }
            if (rel.getActor() != null) {
                rel.getActor().getMovieActorRelations().remove(rel);
            }
            em.remove(rel);
            em.getTransaction().commit();
            this.relations.removeIf(r -> r.getId().equals(id));
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Could not delete movie-actor relation", e);
        }
    }
}
