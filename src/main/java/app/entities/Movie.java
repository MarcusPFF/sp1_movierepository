package app.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ManyToAny;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Entity

public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String original_language;
    private String original_title;
    private String overview;
    private double popularity;
    private String release_date;
    private String title;
    private double vote_average;
    private int vote_count;

    @ManyToMany(mappedBy = "genres")
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany(mappedBy = "actor")
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Actor> actors = new HashSet<>();

    @ManyToMany(mappedBy = "directors")
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Director> directors = new HashSet<>();

}
