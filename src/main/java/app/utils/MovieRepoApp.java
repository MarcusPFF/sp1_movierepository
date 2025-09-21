package app.utils;

import app.daos.*;
import app.entities.*;
import app.mappers.ActorMapper;
import app.mappers.DirectorMapper;
import app.mappers.GenreMapper;
import app.mappers.MovieMapper;
import app.services.ImportService;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class MovieRepoApp {

    private final EntityManagerFactory emf;
    private final ImportService importService;
    private final MovieMapper movieMapper;
    private final GenreMapper genreMapper;
    private final DirectorMapper directorMapper;
    private final ActorMapper actorMapper;
    private AllEntitiesLists lists;

    public MovieRepoApp(EntityManagerFactory emf) {
        this.emf = emf;
        this.importService = new ImportService();
        this.movieMapper = new MovieMapper();
        this.genreMapper = new GenreMapper();
        this.directorMapper = new DirectorMapper();
        this.actorMapper = new ActorMapper();
    }

    public void firstTimeRun() throws InterruptedException {
        loadDataAnExecuteDatabaseOperations();
    }

    private void loadDataAnExecuteDatabaseOperations() throws InterruptedException {
        System.out.println("Loading data...");
        long startTime = System.currentTimeMillis();
        AllEntitiesLists lists = importService.movieService();
        long endTime = System.currentTimeMillis();
        System.out.println("Task runtime: " + (endTime - startTime) + " ms");

        System.out.println("Executing database operations...");
        long startTime2 = System.currentTimeMillis();
        DaoHandler daohandler = new DaoHandler(new MovieDAO(emf), new ActorsDAO(emf), new DirectorsDAO(emf), new MovieActorRelationsDAO(emf), new MovieDirectorRelationsDAO(emf), lists);
        daohandler.executeDatabaseOperations();
        long endTime2 = System.currentTimeMillis();
        System.out.println("Database operations runtime: " + (endTime2 - startTime2) + " ms");
    }

    public void showTop10HighestRatedMovies() {
        List<Movie> movies = movieMapper.top10HighestRatedMovies(emf);
        movies.forEach(System.out::println);
    }

    public void showTop10LowestRatedMovies() {
        List<Movie> movies = movieMapper.top10LowestRatedMovies(emf);
        movies.forEach(System.out::println);
    }

    public void showTop10PopularMovies() {
        List<Movie> movies = movieMapper.top10PopularMovies(emf);
        movies.forEach(System.out::println);
    }

    public void showMoviesByGenre(String genreName) {
        List<Movie> movies = movieMapper.listMoviesByGenreName(genreName, emf);
        movies.forEach(System.out::println);
    }

    public void showAllMovies() {
        List<Movie> movies = movieMapper.listAllMovies(emf);
        movies.forEach(System.out::println);
    }

    public void searchMoviesByTitle(String title) {
        List<Movie> movies = movieMapper.findMoviesByTitle(title, emf);
        movies.forEach(System.out::println);
    }

    public void showAllGenres() {
        List<Genre> genres = genreMapper.listOfAllGenres(emf);
        genres.forEach(System.out::println);
    }

    public void showAllDirectors() {
        List<Director> directors = directorMapper.listOfAllDirectors(emf);
        directors.forEach(System.out::println);
    }

    public void showAllActors() {
        List<Actor> actors = actorMapper.listOfAllActors(emf);
        actors.forEach(System.out::println);
    }
}
