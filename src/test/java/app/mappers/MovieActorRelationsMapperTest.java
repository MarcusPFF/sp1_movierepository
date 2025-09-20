package app.mappers;

import app.entities.Actor;
import app.entities.Movie;
import app.entities.MovieActorRelations;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MovieActorRelationsMapperTest {

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

        Actor actor = Actor.builder()
                .id(1)
                .actorsName("Test Actor")
                .popularity(85.0)
                .build();

        int castId = 123;
        String characterName = "Hero";

        // Act
        MovieActorRelations result = MovieActorRelationsMapper.toEntity(movie, actor, castId, characterName);

        // Assert
        assertNotNull(result);
        assertEquals(movie, result.getMovie());
        assertEquals(actor, result.getActor());
        assertEquals(castId, result.getCastId());
        assertEquals(characterName, result.getCharacterName());
    }

    @Test
    void testToEntityWithNullMovie() {
        // Arrange
        Actor actor = Actor.builder()
                .id(1)
                .actorsName("Test Actor")
                .popularity(85.0)
                .build();

        int castId = 123;
        String characterName = "Hero";

        // Act
        MovieActorRelations result = MovieActorRelationsMapper.toEntity(null, actor, castId, characterName);

        // Assert
        assertNotNull(result);
        assertNull(result.getMovie());
        assertEquals(actor, result.getActor());
        assertEquals(castId, result.getCastId());
        assertEquals(characterName, result.getCharacterName());
    }

    @Test
    void testToEntityWithNullActor() {
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

        int castId = 123;
        String characterName = "Hero";

        // Act
        MovieActorRelations result = MovieActorRelationsMapper.toEntity(movie, null, castId, characterName);

        // Assert
        assertNotNull(result);
        assertEquals(movie, result.getMovie());
        assertNull(result.getActor());
        assertEquals(castId, result.getCastId());
        assertEquals(characterName, result.getCharacterName());
    }

    @Test
    void testToEntityWithNullCharacterName() {
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

        Actor actor = Actor.builder()
                .id(1)
                .actorsName("Test Actor")
                .popularity(85.0)
                .build();

        int castId = 123;

        // Act
        MovieActorRelations result = MovieActorRelationsMapper.toEntity(movie, actor, castId, null);

        // Assert
        assertNotNull(result);
        assertEquals(movie, result.getMovie());
        assertEquals(actor, result.getActor());
        assertEquals(castId, result.getCastId());
        assertNull(result.getCharacterName());
    }

    @Test
    void testToEntityWithZeroCastId() {
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

        Actor actor = Actor.builder()
                .id(1)
                .actorsName("Test Actor")
                .popularity(85.0)
                .build();

        int castId = 0;
        String characterName = "Hero";

        // Act
        MovieActorRelations result = MovieActorRelationsMapper.toEntity(movie, actor, castId, characterName);

        // Assert
        assertNotNull(result);
        assertEquals(movie, result.getMovie());
        assertEquals(actor, result.getActor());
        assertEquals(0, result.getCastId());
        assertEquals(characterName, result.getCharacterName());
    }

    @Test
    void testToEntityWithNegativeCastId() {
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

        Actor actor = Actor.builder()
                .id(1)
                .actorsName("Test Actor")
                .popularity(85.0)
                .build();

        int castId = -1;
        String characterName = "Hero";

        // Act
        MovieActorRelations result = MovieActorRelationsMapper.toEntity(movie, actor, castId, characterName);

        // Assert
        assertNotNull(result);
        assertEquals(movie, result.getMovie());
        assertEquals(actor, result.getActor());
        assertEquals(-1, result.getCastId());
        assertEquals(characterName, result.getCharacterName());
    }

    @Test
    void testToEntityWithEmptyCharacterName() {
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

        Actor actor = Actor.builder()
                .id(1)
                .actorsName("Test Actor")
                .popularity(85.0)
                .build();

        int castId = 123;
        String characterName = "";

        // Act
        MovieActorRelations result = MovieActorRelationsMapper.toEntity(movie, actor, castId, characterName);

        // Assert
        assertNotNull(result);
        assertEquals(movie, result.getMovie());
        assertEquals(actor, result.getActor());
        assertEquals(castId, result.getCastId());
        assertEquals("", result.getCharacterName());
    }

    @Test
    void testToEntityWithSpecialCharactersInCharacterName() {
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

        Actor actor = Actor.builder()
                .id(1)
                .actorsName("Test Actor")
                .popularity(85.0)
                .build();

        int castId = 123;
        String characterName = "Character with special chars: !@#$%^&*()";

        // Act
        MovieActorRelations result = MovieActorRelationsMapper.toEntity(movie, actor, castId, characterName);

        // Assert
        assertNotNull(result);
        assertEquals(movie, result.getMovie());
        assertEquals(actor, result.getActor());
        assertEquals(castId, result.getCastId());
        assertEquals("Character with special chars: !@#$%^&*()", result.getCharacterName());
    }
}
