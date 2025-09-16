package app.entities;

import java.util.List;

public class AllEntitiesLists {
    List<Director> directors;
    List<Genre> genres;
    List<Actor> actors;
    List<Movie> movies;
    List<MovieActorRelations> movieActorRelations;
    List<MovieDirectorRelations> movieDirectorRelations;

    public AllEntitiesLists(List<Director> directors, List<Genre> genres, List<Actor> actors, List<Movie> movies, List<MovieActorRelations> movieActorRelations, List<MovieDirectorRelations> movieDirectorRelations) {
        this.directors = directors;
        this.genres = genres;
        this.actors = actors;
        this.movies = movies;
        this.movieActorRelations = movieActorRelations;
        this.movieDirectorRelations = movieDirectorRelations;
    }
}
