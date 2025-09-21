package app.daos;

import app.config.HibernateConfig;
import app.entities.Actor;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActorsDAOTest {

    private ActorsDAO actorsDAO;
    private EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        HibernateConfig.setTest(true);
        String testDbName = "test_actors_" + System.currentTimeMillis();
        emf = HibernateConfig.getEntityManagerFactoryForTest(testDbName);
        actorsDAO = new ActorsDAO(emf);
    }

    @AfterEach
    void tearDown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
        HibernateConfig.setEmfTest(null);
        HibernateConfig.setTest(false);
    }

    @Test
    void testCreateSingleActor() {
        // Arrange
        Actor actor = Actor.builder()
                .actorsName("Test Actor")
                .popularity(85.5)
                .build();

        // Act
        Actor result = actorsDAO.create(actor);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Test Actor", result.getActorsName());
        assertEquals(85.5, result.getPopularity());
    }

    @Test
    void testCreateMultipleActors() {
        // Arrange
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

        List<Actor> actors = Arrays.asList(actor1, actor2);

        // Act
        List<Actor> result = actorsDAO.create(actors);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertNotNull(result.get(0).getId());
        assertNotNull(result.get(1).getId());
    }

    @Test
    void testGetById() {
        // Arrange
        Actor actor = Actor.builder()
                .actorsName("Test Actor")
                .popularity(85.5)
                .build();

        actorsDAO.create(actor);
        Integer actorId = actor.getId();

        // Act
        Actor result = actorsDAO.getById(actorId);

        // Assert
        assertNotNull(result);
        assertEquals(actorId, result.getId());
        assertEquals("Test Actor", result.getActorsName());
        assertEquals(85.5, result.getPopularity());
    }

    @Test
    void testGetByIdNotFound() {
        // Act
        Actor result = actorsDAO.getById(99999);

        // Assert
        assertNull(result);
    }

    @Test
    void testGetAll() {
        // Arrange
        Actor actor1 = Actor.builder()
                .actorsName("Actor 1")
                .popularity(80.0)
                .build();

        Actor actor2 = Actor.builder()
                .actorsName("Actor 2")
                .popularity(90.0)
                .build();

        actorsDAO.create(actor1);
        actorsDAO.create(actor2);

        // Act
        List<Actor> result = actorsDAO.getAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.size() >= 2);
    }

    @Test
    void testUpdate() {
        // Arrange
        Actor actor = Actor.builder()
                .actorsName("Original Name")
                .popularity(75.0)
                .build();

        actorsDAO.create(actor);
        Integer actorId = actor.getId();

        // Update the actor
        actor.setActorsName("Updated Name");
        actor.setPopularity(95.0);

        // Act
        Actor result = actorsDAO.update(actor);

        // Assert
        assertNotNull(result);
        assertEquals(actorId, result.getId());
        assertEquals("Updated Name", result.getActorsName());
        assertEquals(95.0, result.getPopularity());
    }

    @Test
    void testUpdateNonExistentActor() {
        // Arrange
        Actor actor = Actor.builder()
                .id(99999)
                .actorsName("Non-existent Actor")
                .popularity(75.0)
                .build();

        // Act
        Actor result = actorsDAO.update(actor);

        // Assert
        assertNull(result);
    }

    @Test
    void testDelete() {
        // Arrange
        Actor actor = Actor.builder()
                .actorsName("Actor to Delete")
                .popularity(75.0)
                .build();

        actorsDAO.create(actor);
        Integer actorId = actor.getId();

        // Act
        boolean result = actorsDAO.delete(actorId);

        // Assert
        assertTrue(result);
        assertNull(actorsDAO.getById(actorId));
    }

    @Test
    void testDeleteNonExistentActor() {
        // Act
        boolean result = actorsDAO.delete(99999);

        // Assert
        assertFalse(result);
    }

    @Test
    void testCreateWithEmptyList() {
        // Arrange
        List<Actor> emptyList = Arrays.asList();

        // Act
        List<Actor> result = actorsDAO.create(emptyList);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testCreateWithNullValues() {
        // Arrange
        Actor actor = Actor.builder()
                .actorsName(null)
                .popularity(0)
                .build();

        // Act & Assert - should not throw exception
        assertDoesNotThrow(() -> actorsDAO.create(actor));
    }
}
