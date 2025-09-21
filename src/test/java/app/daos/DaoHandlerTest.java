package app.daos;

import app.config.HibernateConfig;
import app.entities.*;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DaoHandlerTest {

    private DaoHandler daoHandler;
    private MovieDAO movieDAO;
    private ActorsDAO actorsDAO;
    private DirectorsDAO directorsDAO;
    private MovieActorRelationsDAO movieActorRelationsDAO;
    private MovieDirectorRelationsDAO movieDirectorRelationsDAO;
    private AllEntitiesLists allEntitiesLists;
    private EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        HibernateConfig.setTest(true);
        // Use unique database name for each test to avoid conflicts
        String testDbName = "test_dao_handler_" + System.currentTimeMillis();
        emf = HibernateConfig.getEntityManagerFactoryForTest(testDbName);
        movieDAO = new MovieDAO(emf);
        actorsDAO = new ActorsDAO(emf);
        directorsDAO = new DirectorsDAO(emf);
        movieActorRelationsDAO = new MovieActorRelationsDAO(emf);
        movieDirectorRelationsDAO = new MovieDirectorRelationsDAO(emf);
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
    void testConstructor() {
        // Arrange
        List<Director> directors = Arrays.asList(
                Director.builder().directorsName("Director 1").popularity(80.0).build()
        );
        List<Actor> actors = Arrays.asList(
                Actor.builder().actorsName("Actor 1").popularity(85.0).build()
        );
        List<Movie> movies = Arrays.asList(
                Movie.builder().originalTitle("Movie 1").releaseDate(LocalDate.of(2023, 1, 1))
                        .voteAverage(8.0).voteCount(100).popularity(75.0)
                        .originalLanguage("en").overview("Overview 1").build()
        );
        List<MovieActorRelations> mar = Arrays.asList();
        List<MovieDirectorRelations> mdr = Arrays.asList();

        allEntitiesLists = new AllEntitiesLists(directors, actors, movies, mar, mdr);

        // Act
        daoHandler = new DaoHandler(movieDAO, actorsDAO, directorsDAO, movieActorRelationsDAO, movieDirectorRelationsDAO, allEntitiesLists);

        // Assert
        assertNotNull(daoHandler);
    }

    @Test
    void testExecuteDatabaseOperationsWithValidData() {
        // Arrange
        List<Director> directors = Arrays.asList(
                Director.builder().id(1).directorsName("Director 1").popularity(80.0).build()
        );
        List<Actor> actors = Arrays.asList(
                Actor.builder().id(1).actorsName("Actor 1").popularity(85.0).build()
        );
        List<Movie> movies = Arrays.asList(
                Movie.builder().id(1).originalTitle("Movie 1").releaseDate(LocalDate.of(2023, 1, 1))
                        .voteAverage(8.0).voteCount(100).popularity(75.0)
                        .originalLanguage("en").overview("Overview 1").build()
        );
        List<MovieActorRelations> mar = Arrays.asList();
        List<MovieDirectorRelations> mdr = Arrays.asList();

        allEntitiesLists = new AllEntitiesLists(directors, actors, movies, mar, mdr);
        daoHandler = new DaoHandler(movieDAO, actorsDAO, directorsDAO, movieActorRelationsDAO, movieDirectorRelationsDAO, allEntitiesLists);

        // Act
        assertDoesNotThrow(() -> daoHandler.executeDatabaseOperations());

        // Assert - verify that data was persisted by checking if we can retrieve it
        List<Actor> retrievedActors = actorsDAO.getAll();
        List<Director> retrievedDirectors = directorsDAO.getAll();
        List<Movie> retrievedMovies = movieDAO.getAll();

        assertTrue(retrievedActors.size() >= 1);
        assertTrue(retrievedDirectors.size() >= 1);
        assertTrue(retrievedMovies.size() >= 1);
    }

    @Test
    void testExecuteDatabaseOperationsWithEmptyLists() {
        // Arrange
        List<Director> directors = Arrays.asList();
        List<Actor> actors = Arrays.asList();
        List<Movie> movies = Arrays.asList();
        List<MovieActorRelations> mar = Arrays.asList();
        List<MovieDirectorRelations> mdr = Arrays.asList();

        allEntitiesLists = new AllEntitiesLists(directors, actors, movies, mar, mdr);
        daoHandler = new DaoHandler(movieDAO, actorsDAO, directorsDAO, movieActorRelationsDAO, movieDirectorRelationsDAO, allEntitiesLists);

        // Act
        assertDoesNotThrow(() -> daoHandler.executeDatabaseOperations());

        // Assert - should not throw exception even with empty lists
        assertTrue(true);
    }

    @Test
    void testExecuteDatabaseOperationsWithNullAllEntitiesLists() {
        // Arrange
        daoHandler = new DaoHandler(movieDAO, actorsDAO, directorsDAO, movieActorRelationsDAO, movieDirectorRelationsDAO, null);

        // Act
        assertDoesNotThrow(() -> daoHandler.executeDatabaseOperations());

        // Assert - should return early when allEntitiesLists is null
        assertTrue(true);
    }

    @Test
    void testExecuteDatabaseOperationsWithRelations() {
        // Arrange
        List<Director> directors = Arrays.asList(
                Director.builder().id(1).directorsName("Director 1").popularity(80.0).build()
        );
        List<Actor> actors = Arrays.asList(
                Actor.builder().id(1).actorsName("Actor 1").popularity(85.0).build()
        );
        List<Movie> movies = Arrays.asList(
                Movie.builder().id(1).originalTitle("Movie 1").releaseDate(LocalDate.of(2023, 1, 1))
                        .voteAverage(8.0).voteCount(100).popularity(75.0)
                        .originalLanguage("en").overview("Overview 1").build()
        );

        // Create relations
        Movie movie = movies.get(0);
        Director director = directors.get(0);
        Actor actor = actors.get(0);

        List<MovieActorRelations> mar = Arrays.asList(
                MovieActorRelations.builder()
                        .movie(movie)
                        .actor(actor)
                        .castId(1)
                        .characterName("Hero")
                        .build()
        );

        List<MovieDirectorRelations> mdr = Arrays.asList(
                MovieDirectorRelations.builder()
                        .movie(movie)
                        .director(director)
                        .jobTitle("Director")
                        .build()
        );

        allEntitiesLists = new AllEntitiesLists(directors, actors, movies, mar, mdr);
        daoHandler = new DaoHandler(movieDAO, actorsDAO, directorsDAO, movieActorRelationsDAO, movieDirectorRelationsDAO, allEntitiesLists);

        // Act
        assertDoesNotThrow(() -> daoHandler.executeDatabaseOperations());

        // Assert - verify that relations were persisted
        List<MovieActorRelations> retrievedMAR = movieActorRelationsDAO.getAll();
        List<MovieDirectorRelations> retrievedMDR = movieDirectorRelationsDAO.getAll();

        assertTrue(retrievedMAR.size() >= 1);
        assertTrue(retrievedMDR.size() >= 1);
    }

    @Test
    void testExecuteDatabaseOperationsAddsSpecialActor() {
        // Arrange
        List<Director> directors = Arrays.asList();
        List<Actor> actors = Arrays.asList();
        List<Movie> movies = Arrays.asList();
        List<MovieActorRelations> mar = Arrays.asList();
        List<MovieDirectorRelations> mdr = Arrays.asList();

        allEntitiesLists = new AllEntitiesLists(directors, actors, movies, mar, mdr);
        daoHandler = new DaoHandler(movieDAO, actorsDAO, directorsDAO, movieActorRelationsDAO, movieDirectorRelationsDAO, allEntitiesLists);

        // Act
        daoHandler.executeDatabaseOperations();

        // Assert - verify that the special actor "Jonathan Kudsk" was added
        List<Actor> retrievedActors = actorsDAO.getAll();
        boolean foundSpecialActor = retrievedActors.stream()
                .anyMatch(actor -> "Jonathan Kudsk".equals(actor.getActorsName()) && 
                         actor.getPopularity() == 99.999);

        assertTrue(foundSpecialActor, "Special actor 'Jonathan Kudsk' should be added");
    }
}
