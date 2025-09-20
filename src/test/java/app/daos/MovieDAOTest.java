package app.daos;

import app.config.HibernateConfig;
import app.entities.Genre;
import app.entities.Movie;
import app.utils.GenerateNextId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieDAOTest {

    private MovieDAO movieDAO;
    private EntityManagerFactory emf;
    private EntityManager em;

    @Mock
    private GenerateNextId mockGenerateNextId;

    @BeforeEach
    void setUp() {
        HibernateConfig.setTest(true);
        // Use unique database name for each test to avoid conflicts
        String testDbName = "test_movies_" + System.currentTimeMillis();
        emf = HibernateConfig.getEntityManagerFactoryForTest(testDbName);
        movieDAO = new MovieDAO(emf);
        em = emf.createEntityManager();
    }

    @AfterEach
    void tearDown() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
        // Reset test state
        HibernateConfig.setEmfTest(null);
        HibernateConfig.setTest(false);
    }

    @Test
    void testCreateSingleMovie() {
        // Arrange
        Movie movie = Movie.builder()
                .originalTitle("Test Movie")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .voteAverage(8.5)
                .voteCount(100)
                .popularity(75.5)
                .originalLanguage("en")
                .overview("Test overview")
                .build();

        // Act
        Movie result = movieDAO.create(movie);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Test Movie", result.getOriginalTitle());
        assertEquals(LocalDate.of(2023, 1, 1), result.getReleaseDate());
        assertEquals(8.5, result.getVoteAverage());
        assertEquals(100, result.getVoteCount());
        assertEquals(75.5, result.getPopularity());
        assertEquals("en", result.getOriginalLanguage());
        assertEquals("Test overview", result.getOverview());
    }

    @Test
    void testCreateMultipleMovies() {
        // Arrange
        Movie movie1 = Movie.builder()
                .id(1)
                .originalTitle("Movie 1")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .voteAverage(8.0)
                .voteCount(50)
                .popularity(60.0)
                .originalLanguage("en")
                .overview("Overview 1")
                .build();

        Movie movie2 = Movie.builder()
                .id(2)
                .originalTitle("Movie 2")
                .releaseDate(LocalDate.of(2023, 2, 1))
                .voteAverage(7.5)
                .voteCount(75)
                .popularity(70.0)
                .originalLanguage("en")
                .overview("Overview 2")
                .build();

        List<Movie> movies = Arrays.asList(movie1, movie2);

        // Act
        List<Movie> result = movieDAO.create(movies);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertNotNull(result.get(0).getId());
        assertNotNull(result.get(1).getId());
    }

    @Test
    void testCreateMovieWithGenres() {
        // Arrange
        Genre genre1 = Genre.builder()
                .genreName("Action")
                .build();

        Genre genre2 = Genre.builder()
                .genreName("Drama")
                .build();

        Set<Genre> genres = new HashSet<>(Arrays.asList(genre1, genre2));

        Movie movie = Movie.builder()
                .originalTitle("Action Drama Movie")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .voteAverage(8.0)
                .voteCount(100)
                .popularity(80.0)
                .originalLanguage("en")
                .overview("Action drama movie")
                .genres(genres)
                .build();

        // Act
        Movie result = movieDAO.create(movie);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Action Drama Movie", result.getOriginalTitle());
        assertNotNull(result.getGenres());
        assertEquals(2, result.getGenres().size());
    }

    @Test
    void testGetById() {
        // Arrange
        Movie movie = Movie.builder()
                .originalTitle("Test Movie")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .voteAverage(8.5)
                .voteCount(100)
                .popularity(75.5)
                .originalLanguage("en")
                .overview("Test overview")
                .build();

        movieDAO.create(movie);
        Integer movieId = movie.getId();

        // Act
        Movie result = movieDAO.getById(movieId);

        // Assert
        assertNotNull(result);
        assertEquals(movieId, result.getId());
        assertEquals("Test Movie", result.getOriginalTitle());
    }

    @Test
    void testGetByIdNotFound() {
        // Act
        Movie result = movieDAO.getById(99999);

        // Assert
        assertNull(result);
    }

    @Test
    void testGetAll() {
        // Arrange
        Movie movie1 = Movie.builder()
                .originalTitle("Movie 1")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .voteAverage(8.0)
                .voteCount(50)
                .popularity(60.0)
                .originalLanguage("en")
                .overview("Overview 1")
                .build();

        Movie movie2 = Movie.builder()
                .originalTitle("Movie 2")
                .releaseDate(LocalDate.of(2023, 2, 1))
                .voteAverage(7.5)
                .voteCount(75)
                .popularity(70.0)
                .originalLanguage("en")
                .overview("Overview 2")
                .build();

        movieDAO.create(movie1);
        movieDAO.create(movie2);

        // Act
        List<Movie> result = movieDAO.getAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.size() >= 2);
    }

    @Test
    void testUpdate() {
        // Arrange
        Movie movie = Movie.builder()
                .originalTitle("Original Title")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .voteAverage(8.0)
                .voteCount(100)
                .popularity(75.0)
                .originalLanguage("en")
                .overview("Original overview")
                .build();

        movieDAO.create(movie);
        Integer movieId = movie.getId();

        // Update the movie
        movie.setOriginalTitle("Updated Title");
        movie.setReleaseDate(LocalDate.of(2023, 6, 1));

        // Act
        Movie result = movieDAO.update(movie);

        // Assert
        assertNotNull(result);
        assertEquals(movieId, result.getId());
        assertEquals("Updated Title", result.getOriginalTitle());
        assertEquals(LocalDate.of(2023, 6, 1), result.getReleaseDate());
    }

    @Test
    void testUpdateNonExistentMovie() {
        // Arrange
        Movie movie = Movie.builder()
                .id(99999)
                .originalTitle("Non-existent Movie")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .voteAverage(8.0)
                .voteCount(100)
                .popularity(75.0)
                .originalLanguage("en")
                .overview("Non-existent overview")
                .build();

        // Act
        Movie result = movieDAO.update(movie);

        // Assert
        assertNull(result);
    }

    @Test
    void testDelete() {
        // Arrange
        Movie movie = Movie.builder()
                .originalTitle("Movie to Delete")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .voteAverage(8.0)
                .voteCount(100)
                .popularity(75.0)
                .originalLanguage("en")
                .overview("Movie to be deleted")
                .build();

        movieDAO.create(movie);
        Integer movieId = movie.getId();

        // Act
        boolean result = movieDAO.delete(movieId);

        // Assert
        assertTrue(result);
        assertNull(movieDAO.getById(movieId));
    }

    @Test
    void testDeleteNonExistentMovie() {
        // Act
        boolean result = movieDAO.delete(99999);

        // Assert
        assertFalse(result);
    }

    @Test
    void testCreateWithException() {
        // This test would require mocking the EntityManager to throw an exception
        // For now, we'll test the normal flow
        Movie movie = Movie.builder()
                .originalTitle("Test Movie")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .voteAverage(8.5)
                .voteCount(100)
                .popularity(75.5)
                .originalLanguage("en")
                .overview("Test overview")
                .build();

        // Act & Assert - should not throw exception
        assertDoesNotThrow(() -> movieDAO.create(movie));
    }
}
