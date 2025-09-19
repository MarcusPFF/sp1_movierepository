package app;

import app.config.HibernateConfig;
import app.entities.Movie;
import app.mappers.MovieMapper;
import app.services.ImportService;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;


public class Main {
    private static EntityManagerFactory emf;
    private static ImportService is;
    private static MovieMapper mm;

    public static void main(String[] args) throws InterruptedException {
        emf = HibernateConfig.getEntityManagerFactory("movies");
        is = new ImportService();
        mm = new MovieMapper();

        //Load ind i listen
        long startTime = System.currentTimeMillis();
        //AllEntitiesLists lists = is.movieService();
        long endTime = System.currentTimeMillis();
        System.out.println("Task runtime: " + (endTime - startTime) + " ms");

        // Opret DAOerne og DaoHandler
       // DaoHandler daohandler = new DaoHandler(new MovieDAO(emf), new ActorsDAO(emf), new DirectorsDAO(emf), new MovieActorRelationsDAO(emf), new MovieDirectorRelationsDAO(emf), lists);

        //execute database operations
        //Vil merge hvis data i databasen allerede findes
        startTime = System.currentTimeMillis();
        System.out.println("Executing database Operations...");
        //daohandler.executeDatabaseOperations();
        //List<Movie> movies = mm.top10HighestRatedMovies(emf);
        //List<Movie> movies = mm.top10LowestRatedMovies(emf);
        //List<Movie> movies = mm.top10PopularMovies(emf);
        //List<Movie> movies = mm.listAllMovies(emf);
        //List<Movie> movies = mm.findMoviesByTitle("facaden", emf);
        //movies.stream().forEach(System.out::println);
        endTime = System.currentTimeMillis();
        System.out.println("Database operations runtime: " + (endTime - startTime) + " ms");

        emf.close();
    }
}