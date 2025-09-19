package app.mappers;

import app.dtos.GenreDTO;
import app.entities.Genre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GenreMapper {

    public static Genre toEntity(GenreDTO.Genre genre) {
        return Genre.builder()
                .id(genre.getId())
                .genreName(genre.getGenreName())
                .build();
    }

    public List<Genre> listOfAllGenres(EntityManagerFactory emf) {
        try (EntityManager em = emf.createEntityManager()) {
            List<Genre> result = em.createQuery("SELECT g FROM Movie m JOIN m.genres g", Genre.class)
                    .getResultList();
            // Fjern dubletter
            Set<String> seen = new HashSet<>();
            return result.stream()
                    .filter(g -> seen.add(g.getGenreName()))
                    .sorted(Comparator.comparing(Genre::getGenreName))
                    .toList();
        }
    }
}
