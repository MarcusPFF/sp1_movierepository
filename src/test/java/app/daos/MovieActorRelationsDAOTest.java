package app.daos;

import app.config.HibernateConfig;
import app.entities.Actor;
import app.entities.Movie;
import app.entities.MovieActorRelations;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieActorRelationsDAOTest {

    private MovieActorRelationsDAO movieActorRelationsDAO;
    private MovieDAO movieDAO;
    private ActorsDAO actorsDAO;
    private EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        HibernateConfig.setTest(true);
        // Use unique database name for each test to avoid conflicts
        String testDbName = "test_movie_actor_relations_" + System.currentTimeMillis();
        emf = HibernateConfig.getEntityManagerFactoryForTest(testDbName);
        movieActorRelationsDAO = new MovieActorRelationsDAO(emf);
        movieDAO = new MovieDAO(emf);
        actorsDAO = new ActorsDAO(emf);
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

        Actor actor = Actor.builder()
                .actorsName("Test Actor")
                .popularity(85.0)
                .build();

        movieDAO.create(movie);
        actorsDAO.create(actor);

        MovieActorRelations relation = MovieActorRelations.builder()
                .movie(movie)
                .actor(actor)
                .castId(1)
                .characterName("Hero")
                .build();

        // Act
        MovieActorRelations result = movieActorRelationsDAO.create(relation);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(movie.getId(), result.getMovie().getId());
        assertEquals(actor.getId(), result.getActor().getId());
        assertEquals(1, result.getCastId());
        assertEquals("Hero", result.getCharacterName());
    }

    @Test
    void testCreateMultipleRelations() {
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

        Actor actor1 = Actor.builder()
                .id(1)
                .actorsName("Actor 1")
                .popularity(80.0)
                .build();

        Actor actor2 = Actor.builder()
                .id(2)
                .actorsName("Actor 2")
                .popularity(90.0)
                .build();

        movieDAO.create(movie);
        actorsDAO.create(actor1);
        actorsDAO.create(actor2);

        MovieActorRelations relation1 = MovieActorRelations.builder()
                .movie(movie)
                .actor(actor1)
                .castId(1)
                .characterName("Hero")
                .build();

        MovieActorRelations relation2 = MovieActorRelations.builder()
                .movie(movie)
                .actor(actor2)
                .castId(2)
                .characterName("Villain")
                .build();

        List<MovieActorRelations> relations = Arrays.asList(relation1, relation2);

        // Act
        List<MovieActorRelations> result = movieActorRelationsDAO.create(relations);

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

        Actor actor = Actor.builder()
                .actorsName("Test Actor")
                .popularity(85.0)
                .build();

        movieDAO.create(movie);
        actorsDAO.create(actor);

        MovieActorRelations relation = MovieActorRelations.builder()
                .movie(movie)
                .actor(actor)
                .castId(1)
                .characterName("Hero")
                .build();

        movieActorRelationsDAO.create(relation);
        Integer relationId = relation.getId();

        // Act
        MovieActorRelations result = movieActorRelationsDAO.getById(relationId);

        // Assert
        assertNotNull(result);
        assertEquals(relationId, result.getId());
        assertEquals(movie.getId(), result.getMovie().getId());
        assertEquals(actor.getId(), result.getActor().getId());
        assertEquals(1, result.getCastId());
        assertEquals("Hero", result.getCharacterName());
    }

    @Test
    void testGetByIdNotFound() {
        // Act
        MovieActorRelations result = movieActorRelationsDAO.getById(99999);

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

        Actor actor = Actor.builder()
                .actorsName("Test Actor")
                .popularity(85.0)
                .build();

        movieDAO.create(movie);
        actorsDAO.create(actor);

        MovieActorRelations relation = MovieActorRelations.builder()
                .movie(movie)
                .actor(actor)
                .castId(1)
                .characterName("Hero")
                .build();

        movieActorRelationsDAO.create(relation);

        // Act
        List<MovieActorRelations> result = movieActorRelationsDAO.getAll();

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

        Actor actor = Actor.builder()
                .actorsName("Test Actor")
                .popularity(85.0)
                .build();

        movieDAO.create(movie);
        actorsDAO.create(actor);

        MovieActorRelations relation = MovieActorRelations.builder()
                .movie(movie)
                .actor(actor)
                .castId(1)
                .characterName("Original Character")
                .build();

        movieActorRelationsDAO.create(relation);
        Integer relationId = relation.getId();

        // Update the relation
        relation.setCharacterName("Updated Character");
        relation.setCastId(2);

        // Act
        MovieActorRelations result = movieActorRelationsDAO.update(relation);

        // Assert
        assertNotNull(result);
        assertEquals(relationId, result.getId());
        assertEquals("Updated Character", result.getCharacterName());
        assertEquals(2, result.getCastId());
    }

    @Test
    void testUpdateNonExistentRelation() {
        // Arrange
        MovieActorRelations relation = MovieActorRelations.builder()
                .id(99999)
                .castId(1)
                .characterName("Non-existent Character")
                .build();

        // Act
        MovieActorRelations result = movieActorRelationsDAO.update(relation);

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

        Actor actor = Actor.builder()
                .actorsName("Test Actor")
                .popularity(85.0)
                .build();

        movieDAO.create(movie);
        actorsDAO.create(actor);

        MovieActorRelations relation = MovieActorRelations.builder()
                .movie(movie)
                .actor(actor)
                .castId(1)
                .characterName("Hero")
                .build();

        movieActorRelationsDAO.create(relation);
        Integer relationId = relation.getId();

        // Act
        boolean result = movieActorRelationsDAO.delete(relationId);

        // Assert
        assertTrue(result);
        assertNull(movieActorRelationsDAO.getById(relationId));
    }

    @Test
    void testDeleteNonExistentRelation() {
        // Act
        boolean result = movieActorRelationsDAO.delete(99999);

        // Assert
        assertFalse(result);
    }

    @Test
    void testCreateWithEmptyList() {
        // Arrange
        List<MovieActorRelations> emptyList = Arrays.asList();

        // Act
        List<MovieActorRelations> result = movieActorRelationsDAO.create(emptyList);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testCreateWithNullValues() {
        // Arrange
        MovieActorRelations relation = MovieActorRelations.builder()
                .movie(null)
                .actor(null)
                .castId(null)
                .characterName(null)
                .build();

        // Act & Assert - should not throw exception
        assertDoesNotThrow(() -> movieActorRelationsDAO.create(relation));
    }
}
