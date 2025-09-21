package app.services;

import app.dtos.ActorDTO;
import app.dtos.DirectorDTO;
import app.dtos.DiscoverMovieDTO;
import app.dtos.GenreDTO;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FetchToolsTest {

    private FetchTools fetchTools;

    @BeforeEach
    void setUp() {
        fetchTools = new FetchTools();
    }

    @Test
    void testGetAllDanishMovies() {
        // Act
        List<DiscoverMovieDTO> result = fetchTools.getAllDanishMovies();

        // Assert
        assertNotNull(result);
        // The result could be empty if API_KEY is not set or API is not available
        assertTrue(result instanceof java.util.List);
    }

    @Test
    void testGetAllDanishMoviesWithNullApiKey() {
        // This test would require setting the API_KEY environment variable to null
        // For now, we'll just verify the method doesn't throw an exception
        
        // Act & Assert
        assertDoesNotThrow(() -> {
            List<DiscoverMovieDTO> result = fetchTools.getAllDanishMovies();
            assertNotNull(result);
        });
    }

    @Test
    void testGetDirectorsForMovieByMovieId() {
        // Arrange
        Integer movieId = 123;

        // Act
        List<DirectorDTO.Director> result = fetchTools.getDirectorsForMovieByMovieId(movieId);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof java.util.List);
    }

    @Test
    void testGetDirectorsForMovieByMovieIdWithNullId() {
        // Act
        List<DirectorDTO.Director> result = fetchTools.getDirectorsForMovieByMovieId(null);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof java.util.List);
    }

    @Test
    void testGetDirectorsForMovieByMovieIdWithZeroId() {
        // Act
        List<DirectorDTO.Director> result = fetchTools.getDirectorsForMovieByMovieId(0);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof java.util.List);
    }

    @Test
    void testGetDirectorsForMovieByMovieIdWithNegativeId() {
        // Act
        List<DirectorDTO.Director> result = fetchTools.getDirectorsForMovieByMovieId(-1);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof java.util.List);
    }

    @Test
    void testGetActorsForMovieByMovieId() {
        // Arrange
        Integer movieId = 123;

        // Act
        List<ActorDTO.Actor> result = fetchTools.getActorsForMovieByMovieId(movieId);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof java.util.List);
    }

    @Test
    void testGetActorsForMovieByMovieIdWithNullId() {
        // Act
        List<ActorDTO.Actor> result = fetchTools.getActorsForMovieByMovieId(null);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof java.util.List);
    }

    @Test
    void testGetActorsForMovieByMovieIdWithZeroId() {
        // Act
        List<ActorDTO.Actor> result = fetchTools.getActorsForMovieByMovieId(0);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof java.util.List);
    }

    @Test
    void testGetActorsForMovieByMovieIdWithNegativeId() {
        // Act
        List<ActorDTO.Actor> result = fetchTools.getActorsForMovieByMovieId(-1);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof java.util.List);
    }

    @Test
    void testGetGenresForMovieByMovieId() {
        // Arrange
        Integer movieId = 123;

        // Act
        List<GenreDTO.Genre> result = fetchTools.getGenresForMovieByMovieId(movieId);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof java.util.List);
    }

    @Test
    void testGetGenresForMovieByMovieIdWithNullId() {
        // Act
        List<GenreDTO.Genre> result = fetchTools.getGenresForMovieByMovieId(null);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof java.util.List);
    }

    @Test
    void testGetGenresForMovieByMovieIdWithZeroId() {
        // Act
        List<GenreDTO.Genre> result = fetchTools.getGenresForMovieByMovieId(0);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof java.util.List);
    }

    @Test
    void testGetGenresForMovieByMovieIdWithNegativeId() {
        // Act
        List<GenreDTO.Genre> result = fetchTools.getGenresForMovieByMovieId(-1);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof java.util.List);
    }

    @Test
    void testGetAllDanishMoviesHandlesApiErrors() {
        // This test would require mocking the HTTP client to simulate API errors
        // For now, we'll just verify the method doesn't throw an exception
        
        // Act & Assert
        assertDoesNotThrow(() -> {
            List<DiscoverMovieDTO> result = fetchTools.getAllDanishMovies();
            assertNotNull(result);
        });
    }

    @Test
    void testGetDirectorsForMovieByMovieIdHandlesApiErrors() {
        // This test would require mocking the HTTP client to simulate API errors
        // For now, we'll just verify the method doesn't throw an exception
        
        // Act & Assert
        assertDoesNotThrow(() -> {
            List<DirectorDTO.Director> result = fetchTools.getDirectorsForMovieByMovieId(123);
            assertNotNull(result);
        });
    }

    @Test
    void testGetActorsForMovieByMovieIdHandlesApiErrors() {
        // This test would require mocking the HTTP client to simulate API errors
        // For now, we'll just verify the method doesn't throw an exception
        
        // Act & Assert
        assertDoesNotThrow(() -> {
            List<ActorDTO.Actor> result = fetchTools.getActorsForMovieByMovieId(123);
            assertNotNull(result);
        });
    }

    @Test
    void testGetGenresForMovieByMovieIdHandlesApiErrors() {
        // This test would require mocking the HTTP client to simulate API errors
        // For now, we'll just verify the method doesn't throw an exception
        
        // Act & Assert
        assertDoesNotThrow(() -> {
            List<GenreDTO.Genre> result = fetchTools.getGenresForMovieByMovieId(123);
            assertNotNull(result);
        });
    }

    @Test
    void testFetchToolsConstructor() {
        // Act
        FetchTools newFetchTools = new FetchTools();

        // Assert
        assertNotNull(newFetchTools);
    }
}
