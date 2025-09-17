package app;

import app.config.HibernateConfig;
import app.daos.*;
import app.entities.AllEntitiesLists;
import app.services.ImportService;
import jakarta.persistence.EntityManagerFactory;


public class Main {
    private static EntityManagerFactory emf;
    private static ImportService is;

    public static void main(String[] args) throws InterruptedException {
        emf = HibernateConfig.getEntityManagerFactory("movies");
        is = new ImportService();

        //Load ind i listen
        long startTime = System.currentTimeMillis();
        AllEntitiesLists lists = is.movieService();
        long endTime = System.currentTimeMillis();
        System.out.println("Task runtime: " + (endTime - startTime) + " ms");

        // Opret DAOerne og DaoHandler
        DaoHandler daohandler = new DaoHandler(new MovieDAO(emf), new ActorsDAO(emf), new DirectorsDAO(emf), new MovieActorRelationsDAO(emf), new MovieDirectorRelationsDAO(emf), lists);

        //execute database operations
        //Vil merge hvis data i databasen allerede findes
        startTime = System.currentTimeMillis();
        System.out.println("Executing database Operations...");
        daohandler.executeDatabaseOperations();
        endTime = System.currentTimeMillis();
        System.out.println("Database operations runtime: " + (endTime - startTime) + " ms");

        emf.close();
    }
}