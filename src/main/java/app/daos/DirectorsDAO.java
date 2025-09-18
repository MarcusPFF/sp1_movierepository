package app.daos;

import app.entities.Director;
import app.utils.GenerateNextId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class DirectorsDAO implements IDAO<Director, Integer> {
    private final EntityManagerFactory emf;
    private final List<Director> directors;
    private final GenerateNextId gni;

    public DirectorsDAO(EntityManagerFactory emf) {
        this.emf = emf;
        this.directors = new ArrayList<>();
        gni = new GenerateNextId(emf);
    }

    @Override
    public List<Director> create(List<Director> directors) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            for (Director director : directors) {
                //director.setId(null);
                em.merge(director);
                if (this.directors.contains(director)){
                } else {
                    this.directors.add(director);
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Could not persist directors", e);
        }
        return directors;
    }

    @Override
    public Director create(Director director) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            //TODO test om denne metode virker nu med det nye kode neden under.
            director.setId(gni.generateNextId(Director.class));
            em.persist(director);
            em.getTransaction().commit();
            this.directors.add(director);
            return director;
        } catch (Exception e) {
            throw new RuntimeException("Could not persist director", e);
        }
    }

    @Override
    public Director getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Director.class, id);
        }
    }

    @Override
    public List<Director> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT d FROM Director d", Director.class).getResultList();
        }
    }

    @Override
    public Director update(Director director) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Director existing = em.find(Director.class, director.getId());
            if (existing == null) {
                em.getTransaction().rollback();
                return null;
            }
            existing.setDirectorsName(director.getDirectorsName());
            existing.setPopularity(director.getPopularity());

            Director merged = em.merge(existing);
            em.getTransaction().commit();
            this.directors.removeIf(d -> d.getId().equals(merged.getId()));
            this.directors.add(merged);
            return merged;
        } catch (Exception e) {
            throw new RuntimeException("Could not update director", e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Director director = em.find(Director.class, id);
            if (director == null) {
                em.getTransaction().rollback();
                return false;
            }
            em.remove(director);
            em.getTransaction().commit();
            for (int i = 0; i < directors.size(); i++) {
                if (directors.get(i).getId().equals(id)) {
                    directors.remove(i);
                    break;
                }
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Could not delete director", e);
        }
    }
}
