package app.services;

import app.dtos.ActorDTO;
import app.dtos.DirectorDTO;
import app.dtos.DiscoverMovieDTO;
import app.dtos.GenreDTO;
import app.entities.*;
import app.mappers.ActorMapper;
import app.mappers.DirectorMapper;
import app.mappers.MovieMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

import static app.mappers.GenreMapper.toEntity;
import static app.mappers.MovieActorRelationsMapper.toEntity;
import static app.mappers.MovieDirectorRelationsMapper.toEntity;


public class ImportService {

    public AllEntitiesLists movieService() throws InterruptedException {
        FetchTools newFetch = new FetchTools();
        List<DiscoverMovieDTO> movieDtos = newFetch.getAllDanishMovies();

        // laver listen trådsikker fordi flere threads tilføjer til den samtidig
        List<Movie> movies = Collections.synchronizedList(new ArrayList<>());
        List<Director> directors = Collections.synchronizedList(new ArrayList<>());
        List<Actor> actors = Collections.synchronizedList(new ArrayList<>());
        List<MovieActorRelations> movieActorRelations = Collections.synchronizedList(new ArrayList<>());
        List<MovieDirectorRelations> movieDirectorRelations = Collections.synchronizedList(new ArrayList<>());

        List<Callable<Void>> tasks = new ArrayList<>();
        for (DiscoverMovieDTO dto : movieDtos) {
            for (DiscoverMovieDTO.MovieResult movieResult : dto.getResults()) {
                tasks.add(() -> {
                    // --- Alt dette sker i en tråd pr movieresult
                    Movie movie = MovieMapper.toEntity(movieResult);


                    // --- Hent relaterede ting ---
                    List<DirectorDTO.Director> directorsForMovie = newFetch.getDirectorsForMovieByMovieId(movieResult.getId());

                    List<ActorDTO.Actor> actorsForMovie = newFetch.getActorsForMovieByMovieId(movieResult.getId());

                    List<GenreDTO.Genre> genresForMovie = newFetch.getGenresForMovieByMovieId(movieResult.getId());


                    // --- Map og lav relationer ---
                    for (DirectorDTO.Director d : directorsForMovie) {
                        Director director = DirectorMapper.toEntity(d);
                        directors.add(director);
                        movieDirectorRelations.add(toEntity(movie, director, d.getJobTitle()));
                    }

                    for (ActorDTO.Actor a : actorsForMovie) {
                        Actor actor = ActorMapper.toEntity(a);
                        actors.add(actor);
                        movieActorRelations.add(toEntity(movie, actor, a.getCastId(), a.getCharacterName()));
                    }

                    for (GenreDTO.Genre g : genresForMovie) {
                        movie.getGenres().add(toEntity(g));
                    }
                    movies.add(movie);
                    System.out.println(movie.getOriginalTitle() + " is fetched");
                    return null;
                });
            }
        }
        // Kør tasks i en tråd-pool
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        List<Future<Void>> futures = executor.invokeAll(tasks);

        // Vent på at alle tasks bliver færdige
        for (Future<Void> f : futures) {
            try {
                f.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        return new AllEntitiesLists(directors, actors, movies, movieActorRelations, movieDirectorRelations);
    }
}