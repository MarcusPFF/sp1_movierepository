package app.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class GenerateNextId {
    private final EntityManagerFactory emf;

    public GenerateNextId(EntityManagerFactory emf){
        this.emf = emf;
    }
    //TODO test om denne metode virker.
    public <T> int generateNextId(Class<T> entityClass) {
        try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT COALESCE(MAX(e.id), 0) FROM " + entityClass.getSimpleName() + " e";
            Number maxId = em.createQuery(jpql, Number.class).getSingleResult();
            return maxId.intValue() + 1;
        }
    }
}
