package app.mappers;

import app.entities.Director;
import app.entities.Movie;
import app.entities.MovieDirectorRelations;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MovieDirectorRelationsMapperTest {

    @Test
    void testToEntity() {
        // Arrange
        Movie movie = Movie.builder()
                .id(1)
                .originalTitle("Test Movie")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .voteAverage(8.0)
                .voteCount(100)
                .popularity(75.0)
                .originalLanguage("en")
                .overview("Test overview")
                .build();

        Director director = Director.builder()
                .id(1)
                .directorsName("Test Director")
                .popularity(85.0)
                .build();

        String jobTitle = "Director";

        // Act
        MovieDirectorRelations result = MovieDirectorRelationsMapper.toEntity(movie, director, jobTitle);

        // Assert
        assertNotNull(result);
        assertEquals(movie, result.getMovie());
        assertEquals(director, result.getDirector());
        assertEquals(jobTitle, result.getJobTitle());
    }

    @Test
    void testToEntityWithNullMovie() {
        // Arrange
        Director director = Director.builder()
                .id(1)
                .directorsName("Test Director")
                .popularity(85.0)
                .build();

        String jobTitle = "Director";

        // Act
        MovieDirectorRelations result = MovieDirectorRelationsMapper.toEntity(null, director, jobTitle);

        // Assert
        assertNotNull(result);
        assertNull(result.getMovie());
        assertEquals(director, result.getDirector());
        assertEquals(jobTitle, result.getJobTitle());
    }

    @Test
    void testToEntityWithNullDirector() {
        // Arrange
        Movie movie = Movie.builder()
                .id(1)
                .originalTitle("Test Movie")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .voteAverage(8.0)
                .voteCount(100)
                .popularity(75.0)
                .originalLanguage("en")
                .overview("Test overview")
                .build();

        String jobTitle = "Director";

        // Act
        MovieDirectorRelations result = MovieDirectorRelationsMapper.toEntity(movie, null, jobTitle);

        // Assert
        assertNotNull(result);
        assertEquals(movie, result.getMovie());
        assertNull(result.getDirector());
        assertEquals(jobTitle, result.getJobTitle());
    }

    @Test
    void testToEntityWithNullJobTitle() {
        // Arrange
        Movie movie = Movie.builder()
                .id(1)
                .originalTitle("Test Movie")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .voteAverage(8.0)
                .voteCount(100)
                .popularity(75.0)
                .originalLanguage("en")
                .overview("Test overview")
                .build();

        Director director = Director.builder()
                .id(1)
                .directorsName("Test Director")
                .popularity(85.0)
                .build();

        // Act
        MovieDirectorRelations result = MovieDirectorRelationsMapper.toEntity(movie, director, null);

        // Assert
        assertNotNull(result);
        assertEquals(movie, result.getMovie());
        assertEquals(director, result.getDirector());
        assertNull(result.getJobTitle());
    }

    @Test
    void testToEntityWithEmptyJobTitle() {
        // Arrange
        Movie movie = Movie.builder()
                .id(1)
                .originalTitle("Test Movie")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .voteAverage(8.0)
                .voteCount(100)
                .popularity(75.0)
                .originalLanguage("en")
                .overview("Test overview")
                .build();

        Director director = Director.builder()
                .id(1)
                .directorsName("Test Director")
                .popularity(85.0)
                .build();

        String jobTitle = "";

        // Act
        MovieDirectorRelations result = MovieDirectorRelationsMapper.toEntity(movie, director, jobTitle);

        // Assert
        assertNotNull(result);
        assertEquals(movie, result.getMovie());
        assertEquals(director, result.getDirector());
        assertEquals("", result.getJobTitle());
    }

    @Test
    void testToEntityWithSpecialCharactersInJobTitle() {
        // Arrange
        Movie movie = Movie.builder()
                .id(1)
                .originalTitle("Test Movie")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .voteAverage(8.0)
                .voteCount(100)
                .popularity(75.0)
                .originalLanguage("en")
                .overview("Test overview")
                .build();

        Director director = Director.builder()
                .id(1)
                .directorsName("Test Director")
                .popularity(85.0)
                .build();

        String jobTitle = "Job with special chars: !@#$%^&*()";

        // Act
        MovieDirectorRelations result = MovieDirectorRelationsMapper.toEntity(movie, director, jobTitle);

        // Assert
        assertNotNull(result);
        assertEquals(movie, result.getMovie());
        assertEquals(director, result.getDirector());
        assertEquals("Job with special chars: !@#$%^&*()", result.getJobTitle());
    }

    @Test
    void testToEntityWithLongJobTitle() {
        // Arrange
        Movie movie = Movie.builder()
                .id(1)
                .originalTitle("Test Movie")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .voteAverage(8.0)
                .voteCount(100)
                .popularity(75.0)
                .originalLanguage("en")
                .overview("Test overview")
                .build();

        Director director = Director.builder()
                .id(1)
                .directorsName("Test Director")
                .popularity(85.0)
                .build();

        String jobTitle = "J".repeat(1000); // Very long job title

        // Act
        MovieDirectorRelations result = MovieDirectorRelationsMapper.toEntity(movie, director, jobTitle);

        // Assert
        assertNotNull(result);
        assertEquals(movie, result.getMovie());
        assertEquals(director, result.getDirector());
        assertEquals("J".repeat(1000), result.getJobTitle());
    }

    @Test
    void testToEntityWithUnicodeCharactersInJobTitle() {
        // Arrange
        Movie movie = Movie.builder()
                .id(1)
                .originalTitle("Test Movie")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .voteAverage(8.0)
                .voteCount(100)
                .popularity(75.0)
                .originalLanguage("en")
                .overview("Test overview")
                .build();

        Director director = Director.builder()
                .id(1)
                .directorsName("Test Director")
                .popularity(85.0)
                .build();

        String jobTitle = "Job with unicode: 中文 日本語 한국어";

        // Act
        MovieDirectorRelations result = MovieDirectorRelationsMapper.toEntity(movie, director, jobTitle);

        // Assert
        assertNotNull(result);
        assertEquals(movie, result.getMovie());
        assertEquals(director, result.getDirector());
        assertEquals("Job with unicode: 中文 日本語 한국어", result.getJobTitle());
    }
}
