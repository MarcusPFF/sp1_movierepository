package app.mappers;

import app.entities.*;

public class MovieActorRelationsMapper {

    public static MovieActorRelations toEntity(Movie movie, Actor actor, int castId) {
        return MovieActorRelations.builder()
                .movie(movie)
                .actor(actor)
                .castId(castId)
                .build();
    }
}
