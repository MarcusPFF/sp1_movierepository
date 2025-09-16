package app;

import app.config.HibernateConfig;
import app.dtos.ActorDTO;
import app.dtos.DirectorDTO;
import app.dtos.DiscoverMovieDTO;
import app.dtos.GenreDTO;
import app.entities.*;
import app.services.FetchTools;
import app.services.ImportService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static ImportService is;

    public static void main(String[] args) throws InterruptedException {

        emf = HibernateConfig.getEntityManagerFactory("movies");
        em = emf.createEntityManager();
        em.getTransaction().begin();
        is = new ImportService();

        FetchTools newFetch = new FetchTools();

        long startTime = System.currentTimeMillis();
        is.movieService();

//        List<DiscoverMovieDTO> movies = newFetch.getAllDanishMovies();

//        List<DiscoverMovieDTO.MovieResult> movieResultsList = new ArrayList<>();
//        List<Movie> moviesAsEntities = new ArrayList<>();
//        List<Genre> genresAsEntities = new ArrayList<>();
//        List<Actor> actorsAsEntities = new ArrayList<>();
//        List<Director> directorsAsEntities = new ArrayList<>();
//
//        List<MovieActorRelations> movieActorRelationsAsEntities = new ArrayList<>();
//        List<MovieDirectorRelations> movieDirectorRelationsAsEntities = new ArrayList<>();
//
//        List<DirectorDTO.Director> directorList = new ArrayList<>();
//        List<ActorDTO.Actor> actorList = new ArrayList<>();
//        List<GenreDTO.Genre> genreList = new ArrayList<>();
//        for (DiscoverMovieDTO discoverMovieDTO : movies){
//            movieResultsList = discoverMovieDTO.getResults();
//
//
//            for (DiscoverMovieDTO.MovieResult movieResult : movieResultsList){
//                Movie movie = new Movie().builder()
//                        .id(movieResult.getId())
//                        .overview(movieResult.getOverview())
//                        .popularity(movieResult.getPopularity())
//                        .originalLanguage(movieResult.getOriginalLanguage())
//                        .originalTitle(movieResult.getOriginalTitle())
//                        .releaseDate(movieResult.getReleaseDate())
//                        .voteAverage(movieResult.getVoteAverage())
//                        .voteCount(movieResult.getVoteCount())
//                        .build();
//                moviesAsEntities.add(movie);
//
//                List<DirectorDTO> directorDTOs = newFetch.getDirectorsForMovieByMovieId(movieResult.getId());
//                for (DirectorDTO directorDTO : directorDTOs) {
//                    directorList = directorDTO.getDirectors();
//                }
//
//                List<ActorDTO> actorDTOs = newFetch.getActorsForMovieByMovieId(movieResult.getId());
//                for (ActorDTO actorDTO : actorDTOs) {
//                    actorList = actorDTO.getActors();
//                }
//
//                List<GenreDTO> genreDTOs = newFetch.getGenresForMovieByMovieId(movieResult.getId());
//                for (GenreDTO genreDTO : genreDTOs){
//                    genreList = genreDTO.getGenres();
//                }
//
//                for (DirectorDTO.Director director : directorList) {
//                    Director director1 = new Director().builder()
//                            .id(director.getId())
//                            .directorsName(director.getDirectorsName())
//                            .popularity(director.getPopularity())
//                            .build();
//                    directorsAsEntities.add(director1);
//
//                    MovieDirectorRelations movieDirectorRelations = new MovieDirectorRelations().builder()
//                            .movie(movie)
//                            .director(director1)
//                            .jobTitle(director.getJobTitle())
//                            .build();
//                    movieDirectorRelationsAsEntities.add(movieDirectorRelations);
//                }
//
//                for (ActorDTO.Actor actor : actorList) {
//                    Actor actor1 = new Actor().builder()
//                            .id(actor.getId())
//                            .actorsName(actor.getActorsName())
//                            .popularity(actor.getPopularity())
//                            .build();
//                    actorsAsEntities.add(actor1);
//
//                    MovieActorRelations movieActorRelations = new MovieActorRelations().builder()
//                            .actor(actor1)
//                            .castId(actor.getCastId())
//                            .movie(movie).build();
//                    movieActorRelationsAsEntities.add(movieActorRelations);
//                }
//
//                for (GenreDTO.Genre genre : genreList) {
//                    Genre genre1 = new Genre().builder()
//                            .id(genre.getId())
//                            .genreName(genre.getGenreName())
//                            .build();
//                    genresAsEntities.add(genre1);
//                }
//
//
//            }
//        }
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