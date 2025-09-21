package app;

import app.config.HibernateConfig;
import app.utils.MovieRepoApp;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    private static EntityManagerFactory emf;
    private boolean is;

    public static void main(String[] args) throws InterruptedException {
        emf = HibernateConfig.getEntityManagerFactory("movies");
        MovieRepoApp movieRepo = new MovieRepoApp(emf);

        /*
        Kør denne og udkommenter bagefter. Denne fetcher film og skriver ned i databasen.
        */
//        movieRepo.firstTimeRun();

        /*
        Du kan nu køre en af følgende metoder (Udkommenter den du vil køre)
        */

//        movieRepo.searchMoviesByTitle("facaden");
//        movieRepo.showMoviesByGenre("Romance");
//        movieRepo.showAllActors();
//        movieRepo.showAllGenres();
//        movieRepo.showAllDirectors();
//        movieRepo.showAllMovies();
//        movieRepo.showTop10PopularMovies();
//        movieRepo.showTop10LowestRatedMovies();
//        movieRepo.showTop10HighestRatedMovies();

        emf.close();
    }
}
