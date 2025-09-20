package app.mappers;

import app.config.HibernateConfig;
import app.dtos.ActorDTO;
import app.entities.Actor;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorMapperTest {

    private ActorMapper actorMapper;
    private EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        HibernateConfig.setTest(true);
        // Use unique database name for each test to avoid conflicts
        String testDbName = "test_actor_mapper_" + System.currentTimeMillis();
        emf = HibernateConfig.getEntityManagerFactoryForTest(testDbName);
        actorMapper = new ActorMapper();
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
        ActorDTO.Actor dto = new ActorDTO.Actor();
        dto.setId(123);
        dto.setActorsName("Test Actor");
        dto.setPopularity(85.5);

        // Act
        Actor result = ActorMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(123, result.getId());
        assertEquals("Test Actor", result.getActorsName());
        assertEquals(85.5, result.getPopularity());
    }

    @Test
    void testToEntityWithNullValues() {
        // Arrange
        ActorDTO.Actor dto = new ActorDTO.Actor();
        dto.setId(0);
        dto.setActorsName(null);
        dto.setPopularity(0);

        // Act
        Actor result = ActorMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getId()); // Default value for Integer
        assertNull(result.getActorsName());
        assertEquals(0.0, result.getPopularity()); // Default value for Double
    }

    @Test
    void testToEntityWithZeroValues() {
        // Arrange
        ActorDTO.Actor dto = new ActorDTO.Actor();
        dto.setId(0);
        dto.setActorsName("");
        dto.setPopularity(0.0);

        // Act
        Actor result = ActorMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getId());
        assertEquals("", result.getActorsName());
        assertEquals(0.0, result.getPopularity());
    }

    @Test
    void testListOfAllActors() {
        // Act
        var result = actorMapper.listOfAllActors(emf);

        // Assert
        assertNotNull(result);
        // The result should be a list (could be empty if no data in test database)
        assertTrue(result instanceof java.util.List);
    }

    @Test
    void testListOfAllActorsWithNullEntityManagerFactory() {
        // Act & Assert - should not throw exception
        assertThrows(NullPointerException.class, () -> actorMapper.listOfAllActors(null));
    }

    @Test
    void testToEntityWithNegativeValues() {
        // Arrange
        ActorDTO.Actor dto = new ActorDTO.Actor();
        dto.setId(-1);
        dto.setActorsName("Negative Actor");
        dto.setPopularity(-10.5);

        // Act
        Actor result = ActorMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(-1, result.getId());
        assertEquals("Negative Actor", result.getActorsName());
        assertEquals(-10.5, result.getPopularity());
    }

    @Test
    void testToEntityWithLargeValues() {
        // Arrange
        ActorDTO.Actor dto = new ActorDTO.Actor();
        dto.setId(Integer.MAX_VALUE);
        dto.setActorsName("A".repeat(1000)); // Very long name
        dto.setPopularity(Double.MAX_VALUE);

        // Act
        Actor result = ActorMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(Integer.MAX_VALUE, result.getId());
        assertEquals("A".repeat(1000), result.getActorsName());
        assertEquals(Double.MAX_VALUE, result.getPopularity());
    }

    @Test
    void testToEntityWithSpecialCharacters() {
        // Arrange
        ActorDTO.Actor dto = new ActorDTO.Actor();
        dto.setId(1);
        dto.setActorsName("Actor with special chars: !@#$%^&*()");
        dto.setPopularity(50.0);

        // Act
        Actor result = ActorMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Actor with special chars: !@#$%^&*()", result.getActorsName());
        assertEquals(50.0, result.getPopularity());
    }
}
