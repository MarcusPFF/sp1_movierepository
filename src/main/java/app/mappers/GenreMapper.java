package app.mappers;

import app.dtos.GenreDTO;
import app.entities.Genre;

public class GenreMapper {

    public static Genre toEntity(GenreDTO.Genre genre) {
        return Genre.builder()
                .id(genre.getId())
                .genreName(genre.getGenreName())
                .build();
    }
}
