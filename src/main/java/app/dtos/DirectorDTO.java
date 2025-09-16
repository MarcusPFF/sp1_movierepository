package app.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectorDTO {

    @JsonProperty("crew")
    private List<Director> directors;


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Director {

        private int id;

        @JsonProperty("name")
        private String directorsName;

        private double popularity;

        @JsonProperty("department")
        private String department;

        @JsonProperty("job")
        private String jobTitle;

    }
}
