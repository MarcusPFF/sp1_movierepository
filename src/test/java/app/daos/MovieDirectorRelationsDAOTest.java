package app.daos;

import app.config.HibernateConfig;
import app.entities.Director;
import app.entities.Movie;
import app.entities.MovieDirectorRelations;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieDirectorRelationsDAOTest {

    private MovieDirectorRelationsDAO movieDirectorRelationsDAO;
    private MovieDAO movieDAO;
    private DirectorsDAO directorsDAO;
    private EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        HibernateConfig.setTest(true);
        // Use unique database name for each test to avoid conflicts
        String testDbName = "test_movie_director_relations_" + System.currentTimeMillis();
        emf = HibernateConfig.getEntityManagerFactoryForTest(testDbName);
        movieDirectorRelationsDAO = new MovieDirectorRelationsDAO(emf);
        movieDAO = new MovieDAO(emf);
        directorsDAO = new DirectorsDAO(emf);
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
    void testCreateSingleRelation() {
        // Arrange
        Movie movie = Movie.builder()
                .originalTitle("Test Movie")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .voteAverage(8.0)
                .voteCount(100)
                .popularity(75.0)
                .originalLanguage("en")
                .overview("Test overview")
                .build();

        Director director = Director.builder()
                .directorsName("Test Director")
                .popularity(85.0)
                .build();

        movieDAO.create(movie);
        directorsDAO.create(director);

        MovieDirectorRelations relation = MovieDirectorRelations.builder()
                .movie(movie)
                .director(director)
                .jobTitle("Director")
                .build();

        // Act
        MovieDirectorRelations result = movieDirectorRelationsDAO.create(relation);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(movie.getId(), result.getMovie().getId());
        assertEquals(director.getId(), result.getDirector().getId());
        assertEquals("Director", result.getJobTitle());
    }

    @Test
    void testCreateMultipleRelations() {
        // Arrange
        Movie movie = Movie.builder()
                .originalTitle("Test Movie")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .voteAverage(8.0)
                .voteCount(100)
                .popularity(75.0)
                .originalLanguage("en")
                .overview("Test overview")
                .build();

        Director director1 = Director.builder()
                .directorsName("Director 1")
                .popularity(80.0)
                .build();

        Director director2 = Director.builder()
                .directorsName("Director 2")
                .popularity(90.0)
                .build();

        movieDAO.create(movie);
        directorsDAO.create(director1);
        directorsDAO.create(director2);

        MovieDirectorRelations relation1 = MovieDirectorRelations.builder()
                .movie(movie)
                .director(director1)
                .jobTitle("Director")
                .build();

        MovieDirectorRelations relation2 = MovieDirectorRelations.builder()
                .movie(movie)
                .director(director2)
                .jobTitle("Co-Director")
                .build();

        List<MovieDirectorRelations> relations = Arrays.asList(relation1, relation2);

        // Act
        List<MovieDirectorRelations> result = movieDirectorRelationsDAO.create(relations);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertNotNull(result.get(0).getId());
        assertNotNull(result.get(1).getId());
    }

    @Test
    void testGetById() {
        // Arrange
        Movie movie = Movie.builder()
                .originalTitle("Test Movie")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .voteAverage(8.0)
                .voteCount(100)
                .popularity(75.0)
                .originalLanguage("en")
                .overview("Test overview")
                .build();

        Director director = Director.builder()
                .directorsName("Test Director")
                .popularity(85.0)
                .build();

        movieDAO.create(movie);
        directorsDAO.create(director);

        MovieDirectorRelations relation = MovieDirectorRelations.builder()
                .movie(movie)
                .director(director)
                .jobTitle("Director")
                .build();

        movieDirectorRelationsDAO.create(relation);
        Integer relationId = relation.getId();

        // Act
        MovieDirectorRelations result = movieDirectorRelationsDAO.getById(relationId);

        // Assert
        assertNotNull(result);
        assertEquals(relationId, result.getId());
        assertEquals(movie.getId(), result.getMovie().getId());
        assertEquals(director.getId(), result.getDirector().getId());
        assertEquals("Director", result.getJobTitle());
    }

    @Test
    void testGetByIdNotFound() {
        // Act
        MovieDirectorRelations result = movieDirectorRelationsDAO.getById(99999);

        // Assert
        assertNull(result);
    }

    @Test
    void testGetAll() {
        // Arrange
        Movie movie = Movie.builder()
                .originalTitle("Test Movie")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .voteAverage(8.0)
                .voteCount(100)
                .popularity(75.0)
                .originalLanguage("en")
                .overview("Test overview")
                .build();

        Director director = Director.builder()
                .directorsName("Test Director")
                .popularity(85.0)
                .build();

        movieDAO.create(movie);
        directorsDAO.create(director);

        MovieDirectorRelations relation = MovieDirectorRelations.builder()
                .movie(movie)
                .director(director)
                .jobTitle("Director")
                .build();

        movieDirectorRelationsDAO.create(relation);

        // Act
        List<MovieDirectorRelations> result = movieDirectorRelationsDAO.getAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.size() >= 1);
    }

    @Test
    void testUpdate() {
        // Arrange
        Movie movie = Movie.builder()
                .originalTitle("Test Movie")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .voteAverage(8.0)
                .voteCount(100)
                .popularity(75.0)
                .originalLanguage("en")
                .overview("Test overview")
                .build();

        Director director = Director.builder()
                .directorsName("Test Director")
                .popularity(85.0)
                .build();

        movieDAO.create(movie);
        directorsDAO.create(director);

        MovieDirectorRelations relation = MovieDirectorRelations.builder()
                .movie(movie)
                .director(director)
                .jobTitle("Original Job")
                .build();

        movieDirectorRelationsDAO.create(relation);
        Integer relationId = relation.getId();

        // Update the relation
        relation.setJobTitle("Updated Job");

        // Act
        MovieDirectorRelations result = movieDirectorRelationsDAO.update(relation);

        // Assert
        assertNotNull(result);
        assertEquals(relationId, result.getId());
        assertEquals("Updated Job", result.getJobTitle());
    }

    @Test
    void testUpdateNonExistentRelation() {
        // Arrange
        MovieDirectorRelations relation = MovieDirectorRelations.builder()
                .id(99999)
                .jobTitle("Non-existent Job")
                .build();

        // Act
        MovieDirectorRelations result = movieDirectorRelationsDAO.update(relation);

        // Assert
        assertNull(result);
    }

    @Test
    void testDelete() {
        // Arrange
        Movie movie = Movie.builder()
                .originalTitle("Test Movie")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .voteAverage(8.0)
                .voteCount(100)
                .popularity(75.0)
                .originalLanguage("en")
                .overview("Test overview")
                .build();

        Director director = Director.builder()
                .directorsName("Test Director")
                .popularity(85.0)
                .build();

        movieDAO.create(movie);
        directorsDAO.create(director);

        MovieDirectorRelations relation = MovieDirectorRelations.builder()
                .movie(movie)
                .director(director)
                .jobTitle("Director")
                .build();

        movieDirectorRelationsDAO.create(relation);
        Integer relationId = relation.getId();

        // Act
        boolean result = movieDirectorRelationsDAO.delete(relationId);

        // Assert
        assertTrue(result);
        assertNull(movieDirectorRelationsDAO.getById(relationId));
    }

    @Test
    void testDeleteNonExistentRelation() {
        // Act
        boolean result = movieDirectorRelationsDAO.delete(99999);

        // Assert
        assertFalse(result);
    }

    @Test
    void testCreateWithEmptyList() {
        // Arrange
        List<MovieDirectorRelations> emptyList = Arrays.asList();

        // Act
        List<MovieDirectorRelations> result = movieDirectorRelationsDAO.create(emptyList);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testCreateWithNullValues() {
        // Arrange
        MovieDirectorRelations relation = MovieDirectorRelations.builder()
                .movie(null)
                .director(null)
                .jobTitle(null)
                .build();

        // Act & Assert - should not throw exception
        assertDoesNotThrow(() -> movieDirectorRelationsDAO.create(relation));
    }
}
