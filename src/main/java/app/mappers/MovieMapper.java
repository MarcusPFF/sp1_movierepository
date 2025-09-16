package app.mappers;

import app.dtos.DiscoverMovieDTO;
import app.entities.Movie;

public class MovieMapper {
    public static Movie toEntity(DiscoverMovieDTO.MovieResult dto) {
        return Movie.builder()
                .id(dto.getId())
                .overview(dto.getOverview())
                .popularity(dto.getPopularity())
                .originalLanguage(dto.getOriginalLanguage())
                .originalTitle(dto.getOriginalTitle())
                .releaseDate(dto.getReleaseDate())
                .voteAverage(dto.getVoteAverage())
                .voteCount(dto.getVoteCount())
                .build();
    }
}
