package app.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenreDTO {

    @JsonProperty("genres")
    private List<Genre> genres;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Genre {

        private int id;

        @JsonProperty("name")
        private String genreName;
    }

}
