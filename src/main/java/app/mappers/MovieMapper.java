package app.mappers;

import app.dtos.DiscoverMovieDTO;
import app.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

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

    public List<Movie> top10HighestRatedMovies(EntityManagerFactory emf) {
        try (EntityManager em = emf.createEntityManager()) {
            List<Movie> top10Movies = em.createQuery("SELECT m FROM Movie m WHERE voteCount >= 3 ORDER BY m.voteAverage DESC", Movie.class)
                    .setMaxResults(10)
                    .getResultList();
            return top10Movies;
        }
    }

    public List<Movie> top10LowestRatedMovies(EntityManagerFactory emf) {
        try (EntityManager em = emf.createEntityManager()) {
            List<Movie> top10Movies = em.createQuery("SELECT m FROM Movie m WHERE voteCount >= 3  ORDER BY m.voteAverage ASC", Movie.class)
                    .setMaxResults(10)
                    .getResultList();
            return top10Movies;
        }
    }

    public List<Movie> top10PopularMovies(EntityManagerFactory emf) {
        try (EntityManager em = emf.createEntityManager()) {
            List<Movie> top10Movies = em.createQuery("SELECT m FROM Movie m ORDER BY m.popularity DESC", Movie.class)
                    .setMaxResults(10)
                    .getResultList();
            return top10Movies;
        }
    }

    public List<Movie> listMoviesByGenreName(String genreName, EntityManagerFactory emf) {
        if (genreName == null || genreName.trim().isEmpty()) return List.of();

        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT DISTINCT m FROM Movie m " + "JOIN m.genres g " + "WHERE LOWER(g.genreName) = :name " + "ORDER BY m.originalTitle", Movie.class)
                    .setParameter("name", genreName.trim().toLowerCase())
                    .getResultList();
        }
    }

}

