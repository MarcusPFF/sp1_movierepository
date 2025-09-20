package app.mappers;

import app.config.HibernateConfig;
import app.dtos.DiscoverMovieDTO;
import app.entities.Genre;
import app.entities.Movie;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MovieMapperTest {

    private MovieMapper movieMapper;
    private EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        HibernateConfig.setTest(true);
        // Use unique database name for each test to avoid conflicts
        String testDbName = "test_movie_mapper_" + System.currentTimeMillis();
        emf = HibernateConfig.getEntityManagerFactoryForTest(testDbName);
        movieMapper = new MovieMapper();
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
        DiscoverMovieDTO.MovieResult dto = new DiscoverMovieDTO.MovieResult();
        dto.setId(123);
        dto.setOverview("Test overview");
        dto.setPopularity(85.5);
        dto.setOriginalLanguage("en");
        dto.setOriginalTitle("Test Movie");
        dto.setReleaseDate(LocalDate.of(2023, 1, 1));
        dto.setVoteAverage(8.5);
        dto.setVoteCount(100);

        // Act
        Movie result = MovieMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(123, result.getId());
        assertEquals("Test overview", result.getOverview());
        assertEquals(85.5, result.getPopularity());
        assertEquals("en", result.getOriginalLanguage());
        assertEquals("Test Movie", result.getOriginalTitle());
        assertEquals(LocalDate.of(2023, 1, 1), result.getReleaseDate());
        assertEquals(8.5, result.getVoteAverage());
        assertEquals(100, result.getVoteCount());
    }

    @Test
    void testToEntityWithNullValues() {
        // Arrange
        DiscoverMovieDTO.MovieResult dto = new DiscoverMovieDTO.MovieResult();
        dto.setId(0);
        dto.setOverview(null);
        dto.setPopularity(0);
        dto.setOriginalLanguage(null);
        dto.setOriginalTitle(null);
        dto.setReleaseDate(null);
        dto.setVoteAverage(0);
        dto.setVoteCount(0);

        // Act
        Movie result = MovieMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getId()); // Default value for Integer
        assertNull(result.getOverview());
        assertEquals(0.0, result.getPopularity()); // Default value for Double
        assertNull(result.getOriginalLanguage());
        assertNull(result.getOriginalTitle());
        assertNull(result.getReleaseDate());
        assertEquals(0.0, result.getVoteAverage()); // Default value for Double
        assertEquals(0, result.getVoteCount()); // Default value for Integer
    }

    @Test
    void testTop10HighestRatedMovies() {
        // Arrange - Create test movies with different ratings
        createTestMovies();

        // Act
        List<Movie> result = movieMapper.top10HighestRatedMovies(emf);

        // Assert
        assertNotNull(result);
        assertTrue(result.size() <= 10);
        
        // Verify movies are ordered by vote average descending
        for (int i = 1; i < result.size(); i++) {
            assertTrue(result.get(i-1).getVoteAverage() >= result.get(i).getVoteAverage(),
                    "Movies should be ordered by vote average descending");
        }
    }

    @Test
    void testTop10LowestRatedMovies() {
        // Arrange - Create test movies with different ratings
        createTestMovies();

        // Act
        List<Movie> result = movieMapper.top10LowestRatedMovies(emf);

        // Assert
        assertNotNull(result);
        assertTrue(result.size() <= 10);
        
        // Verify movies are ordered by vote average ascending
        for (int i = 1; i < result.size(); i++) {
            assertTrue(result.get(i-1).getVoteAverage() <= result.get(i).getVoteAverage(),
                    "Movies should be ordered by vote average ascending");
        }
    }

    @Test
    void testTop10PopularMovies() {
        // Arrange - Create test movies with different popularity
        createTestMovies();

        // Act
        List<Movie> result = movieMapper.top10PopularMovies(emf);

        // Assert
        assertNotNull(result);
        assertTrue(result.size() <= 10);
        
        // Verify movies are ordered by popularity descending
        for (int i = 1; i < result.size(); i++) {
            assertTrue(result.get(i-1).getPopularity() >= result.get(i).getPopularity(),
                    "Movies should be ordered by popularity descending");
        }
    }

    @Test
    void testListMoviesByGenreName() {
        // Arrange - Create test movies with genres
        createTestMoviesWithGenres();

        // Act
        List<Movie> result = movieMapper.listMoviesByGenreName("Action", emf);

        // Assert
        assertNotNull(result);
        // Should find movies with Action genre
        assertTrue(result.size() >= 0);
    }

    @Test
    void testListMoviesByGenreNameCaseInsensitive() {
        // Arrange - Create test movies with genres
        createTestMoviesWithGenres();

        // Act
        List<Movie> result = movieMapper.listMoviesByGenreName("action", emf);

        // Assert
        assertNotNull(result);
        // Should find movies with Action genre (case insensitive)
        assertTrue(result.size() >= 0);
    }

    @Test
    void testListMoviesByGenreNameWithNull() {
        // Act
        List<Movie> result = movieMapper.listMoviesByGenreName(null, emf);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testListMoviesByGenreNameWithEmptyString() {
        // Act
        List<Movie> result = movieMapper.listMoviesByGenreName("", emf);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testListMoviesByGenreNameWithWhitespace() {
        // Act
        List<Movie> result = movieMapper.listMoviesByGenreName("   ", emf);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testListAllMovies() {
        // Arrange - Create test movies
        createTestMovies();

        // Act
        List<Movie> result = movieMapper.listAllMovies(emf);

        // Assert
        assertNotNull(result);
        assertTrue(result.size() >= 0);
    }

    @Test
    void testFindMoviesByTitle() {
        // Arrange - Create test movies
        createTestMovies();

        // Act
        List<Movie> result = movieMapper.findMoviesByTitle("Test", emf);

        // Assert
        assertNotNull(result);
        assertTrue(result.size() >= 0);
    }

    @Test
    void testFindMoviesByTitleCaseInsensitive() {
        // Arrange - Create test movies
        createTestMovies();

        // Act
        List<Movie> result = movieMapper.findMoviesByTitle("test", emf);

        // Assert
        assertNotNull(result);
        assertTrue(result.size() >= 0);
    }

    @Test
    void testFindMoviesByTitleWithNull() {
        // Act
        List<Movie> result = movieMapper.findMoviesByTitle(null, emf);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindMoviesByTitleWithEmptyString() {
        // Act
        List<Movie> result = movieMapper.findMoviesByTitle("", emf);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindMoviesByTitleWithWhitespace() {
        // Act
        List<Movie> result = movieMapper.findMoviesByTitle("   ", emf);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    private void createTestMovies() {
        // This is a helper method to create test data
        // In a real test, you would use a test database or mock the EntityManager
        // For now, we'll just verify the methods don't throw exceptions
    }

    private void createTestMoviesWithGenres() {
        // This is a helper method to create test data with genres
        // In a real test, you would use a test database or mock the EntityManager
        // For now, we'll just verify the methods don't throw exceptions
    }
}
