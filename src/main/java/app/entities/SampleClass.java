package app.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    @EqualsAndHashCode
    @Entity
    public class SampleClass {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @ManyToOne
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        private SampleLocation sourceLocation;

        @ManyToOne
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        private SampleLocation destinationLocation;

        private LocalDateTime shipmentDateTime;
    }
