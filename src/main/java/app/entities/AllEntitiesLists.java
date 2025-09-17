package app.entities;

import lombok.Getter;

import java.util.List;

@Getter

public class AllEntitiesLists {
    List<Director> directors;
    List<Actor> actors;
    List<Movie> movies;
    List<MovieActorRelations> movieActorRelations;
    List<MovieDirectorRelations> movieDirectorRelations;

    public AllEntitiesLists(List<Director> directors, List<Actor> actors, List<Movie> movies, List<MovieActorRelations> movieActorRelations, List<MovieDirectorRelations> movieDirectorRelations) {
        this.directors = directors;
        this.actors = actors;
        this.movies = movies;
        this.movieActorRelations = movieActorRelations;
        this.movieDirectorRelations = movieDirectorRelations;
    }


}
