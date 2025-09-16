package app.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)

public class SampleDTO {
        private int id;
        private String title;
        @JsonProperty("original_title")
        private String originalTitle;
        @JsonProperty("original_language")
        private String originalLanguage;
        private String overview;
}
