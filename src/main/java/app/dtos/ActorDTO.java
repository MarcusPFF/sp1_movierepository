package app.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActorDTO {

    @JsonProperty("cast")
    private List<Actor> actors;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Actor {

        private int id;

        @JsonProperty("name")
        private String actorsName;

        @JsonProperty("character")
        private String characterName;

        @JsonProperty("cast_id")
        private int castId;

        private double popularity;
    }


}
