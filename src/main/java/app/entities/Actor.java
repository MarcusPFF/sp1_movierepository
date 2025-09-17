package app.entities;

import jakarta.persistence.*;
import lombok.*;

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

public class Actor {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String actorsName;
    private double popularity;

    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<MovieActorRelations> movieActorRelations = new HashSet<>();
}
