package app.mappers;

import app.config.HibernateConfig;
import app.dtos.GenreDTO;
import app.entities.Genre;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenreMapperTest {

    private GenreMapper genreMapper;
    private EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        HibernateConfig.setTest(true);
        // Use unique database name for each test to avoid conflicts
        String testDbName = "test_genre_mapper_" + System.currentTimeMillis();
        emf = HibernateConfig.getEntityManagerFactoryForTest(testDbName);
        genreMapper = new GenreMapper();
    }

    @AfterEach
    void tearDown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
        // Reset test state
        HibernateConfig.setEmfTest(null);
        HibernateConfig.setTest(false);
    }

    @Test
    void testToEntity() {
        // Arrange
        GenreDTO.Genre dto = new GenreDTO.Genre();
        dto.setId(123);
        dto.setGenreName("Action");

        // Act
        Genre result = GenreMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(123, result.getId());
        assertEquals("Action", result.getGenreName());
    }

    @Test
    void testToEntityWithNullValues() {
        // Arrange
        GenreDTO.Genre dto = new GenreDTO.Genre();
        dto.setId(0);
        dto.setGenreName(null);

        // Act
        Genre result = GenreMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getId()); // Default value for Integer
        assertNull(result.getGenreName());
    }

    @Test
    void testToEntityWithZeroValues() {
        // Arrange
        GenreDTO.Genre dto = new GenreDTO.Genre();
        dto.setId(0);
        dto.setGenreName("");

        // Act
        Genre result = GenreMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getId());
        assertEquals("", result.getGenreName());
    }

    @Test
    void testListOfAllGenres() {
        // Act
        var result = genreMapper.listOfAllGenres(emf);

        // Assert
        assertNotNull(result);
        // The result should be a list (could be empty if no data in test database)
        assertTrue(result instanceof java.util.List);
    }

    @Test
    void testListOfAllGenresWithNullEntityManagerFactory() {
        // Act & Assert - should not throw exception

        assertThrows(NullPointerException.class, () -> genreMapper.listOfAllGenres(null));
    }

    @Test
    void testToEntityWithNegativeValues() {
        // Arrange
        GenreDTO.Genre dto = new GenreDTO.Genre();
        dto.setId(-1);
        dto.setGenreName("Negative Genre");

        // Act
        Genre result = GenreMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(-1, result.getId());
        assertEquals("Negative Genre", result.getGenreName());
    }

    @Test
    void testToEntityWithLargeValues() {
        // Arrange
        GenreDTO.Genre dto = new GenreDTO.Genre();
        dto.setId(Integer.MAX_VALUE);
        dto.setGenreName("G".repeat(1000)); // Very long name

        // Act
        Genre result = GenreMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(Integer.MAX_VALUE, result.getId());
        assertEquals("G".repeat(1000), result.getGenreName());
    }

    @Test
    void testToEntityWithSpecialCharacters() {
        // Arrange
        GenreDTO.Genre dto = new GenreDTO.Genre();
        dto.setId(1);
        dto.setGenreName("Genre with special chars: !@#$%^&*()");

        // Act
        Genre result = GenreMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Genre with special chars: !@#$%^&*()", result.getGenreName());
    }

    @Test
    void testToEntityWithUnicodeCharacters() {
        // Arrange
        GenreDTO.Genre dto = new GenreDTO.Genre();
        dto.setId(1);
        dto.setGenreName("Genre with unicode: 中文 日本語 한국어");

        // Act
        Genre result = GenreMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Genre with unicode: 中文 日本語 한국어", result.getGenreName());
    }
}
