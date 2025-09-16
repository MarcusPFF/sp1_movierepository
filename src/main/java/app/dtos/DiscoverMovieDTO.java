package app.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscoverMovieDTO {

    private int page;

    @JsonProperty("results")
    private List<MovieResult> results;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_results")
    private int totalResults;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MovieResult {
        private int id;

        @JsonProperty("original_title")
        private String originalTitle;

        private String overview;

        @JsonProperty("original_language")
        private String originalLanguage;

        @JsonProperty("genre_ids")
        private List<Integer> genreIds;

        private double popularity;

        @JsonProperty("release_date")
        private LocalDate releaseDate;

        @JsonProperty("vote_average")
        private double voteAverage;

        @JsonProperty("vote_count")
        private int voteCount;
    }

    @Override
    public String toString() {
        return "DiscoverMovieDTO{" +
                "page=" + page +
                ", totalPages=" + totalPages +
                ", totalResults=" + totalResults +
                ", results.size=" + (results != null ? results.size() : 0) +
                '}';
    }
}
