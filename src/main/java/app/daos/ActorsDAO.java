package app.daos;

import app.entities.Actor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class ActorsDAO implements IDAO<Actor, Integer> {
    private final EntityManagerFactory emf;
    private final List<Actor> actors;

    public ActorsDAO(EntityManagerFactory emf) {
        this.emf = emf;
        this.actors = new ArrayList<>();
    }

    @Override
    public List<Actor> create(List<Actor> actors) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            for (Actor actor : actors) {
                actor.setId(null);
                em.merge(actor);
                this.actors.add(actor);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Could not persist actors", e);
        }
        return actors;
    }

    @Override
    public Actor create(Actor actor) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(actor);
            em.getTransaction().commit();
            this.actors.add(actor);
            return actor;
        } catch (Exception e) {
            throw new RuntimeException("Could not persist actor", e);
        }
    }

    @Override
    public Actor getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Actor.class, id);
        }
    }

    @Override
    public List<Actor> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT a FROM Actor a", Actor.class).getResultList();
        }
    }

    @Override
    public Actor update(Actor actor) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Actor existing = em.find(Actor.class, actor.getId());
            if (existing == null) {
                em.getTransaction().rollback();
                return null;
            }
            existing.setActorsName(actor.getActorsName());
            existing.setPopularity(actor.getPopularity());

            Actor merged = em.merge(existing);
            em.getTransaction().commit();

            this.actors.removeIf(a -> a.getId().equals(merged.getId()));
            this.actors.add(merged);
            return merged;
        } catch (Exception e) {
            throw new RuntimeException("Could not update actor", e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Actor actor = em.find(Actor.class, id);
            if (actor == null) {
                em.getTransaction().rollback();
                return false;
            }
            em.remove(actor);
            em.getTransaction().commit();
            for (int i = 0; i < actors.size(); i++) {
                if (actors.get(i).getId().equals(id)) {
                    actors.remove(i);
                    break;
                }
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Could not delete actor", e);
        }
    }
}
