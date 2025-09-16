package app;

import app.config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    public static void main(String[] args) {
        emf = HibernateConfig.getEntityManagerFactory("movies");
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.close();
    }
}