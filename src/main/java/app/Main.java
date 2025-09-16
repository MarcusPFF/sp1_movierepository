package app;

import app.config.HibernateConfig;
import app.dtos.DiscoverMovieDTO;
import app.services.FetchTools;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class Main {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    public static void main(String[] args) {
        emf = HibernateConfig.getEntityManagerFactory("movies");
        em = emf.createEntityManager();
        em.getTransaction().begin();

        FetchTools newFetch = new FetchTools();

        long startTime = System.currentTimeMillis();
        List<DiscoverMovieDTO> movies = newFetch.getAllDanishMovies();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Task runtime: " + duration + " milliseconds");

        startTime = System.currentTimeMillis();

        endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        System.out.println("Task runtime: " + duration + " milliseconds");


        em.close();
    }

}