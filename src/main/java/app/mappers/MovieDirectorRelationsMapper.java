package app.mappers;

import app.entities.Director;
import app.entities.Movie;
import app.entities.MovieDirectorRelations;
import app.dtos.DirectorDTO;

public class MovieDirectorRelationsMapper {

    public static MovieDirectorRelations toEntity(Movie movie, Director director, String jobTitle) {
        return MovieDirectorRelations.builder()
                .movie(movie)
                .director(director)
                .jobTitle(jobTitle)
                .build();
    }
}
