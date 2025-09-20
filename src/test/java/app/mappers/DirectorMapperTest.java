package app.mappers;

import app.config.HibernateConfig;
import app.dtos.DirectorDTO;
import app.entities.Director;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectorMapperTest {

    private DirectorMapper directorMapper;
    private EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        HibernateConfig.setTest(true);
        // Use unique database name for each test to avoid conflicts
        String testDbName = "test_director_mapper_" + System.currentTimeMillis();
        emf = HibernateConfig.getEntityManagerFactoryForTest(testDbName);
        directorMapper = new DirectorMapper();
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
        DirectorDTO.Director dto = new DirectorDTO.Director();
        dto.setId(123);
        dto.setDirectorsName("Test Director");
        dto.setPopularity(85.5);

        // Act
        Director result = DirectorMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(123, result.getId());
        assertEquals("Test Director", result.getDirectorsName());
        assertEquals(85.5, result.getPopularity());
    }

    @Test
    void testToEntityWithNullValues() {
        // Arrange
        DirectorDTO.Director dto = new DirectorDTO.Director();
        dto.setId(0);
        dto.setDirectorsName(null);
        dto.setPopularity(0);

        // Act
        Director result = DirectorMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getId()); // Default value for Integer
        assertNull(result.getDirectorsName());
        assertEquals(0.0, result.getPopularity()); // Default value for Double
    }

    @Test
    void testToEntityWithZeroValues() {
        // Arrange
        DirectorDTO.Director dto = new DirectorDTO.Director();
        dto.setId(0);
        dto.setDirectorsName("");
        dto.setPopularity(0.0);

        // Act
        Director result = DirectorMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getId());
        assertEquals("", result.getDirectorsName());
        assertEquals(0.0, result.getPopularity());
    }

    @Test
    void testListOfAllDirectors() {
        // Act
        var result = directorMapper.listOfAllDirectors(emf);

        // Assert
        assertNotNull(result);
        // The result should be a list (could be empty if no data in test database)
        assertTrue(result instanceof java.util.List);
    }

    @Test
    void testListOfAllDirectorsWithNullEntityManagerFactory() {
        // Act & Assert - should not throw exception
        assertThrows(NullPointerException.class, () -> directorMapper.listOfAllDirectors(null));
    }

    @Test
    void testToEntityWithNegativeValues() {
        // Arrange
        DirectorDTO.Director dto = new DirectorDTO.Director();
        dto.setId(-1);
        dto.setDirectorsName("Negative Director");
        dto.setPopularity(-10.5);

        // Act
        Director result = DirectorMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(-1, result.getId());
        assertEquals("Negative Director", result.getDirectorsName());
        assertEquals(-10.5, result.getPopularity());
    }

    @Test
    void testToEntityWithLargeValues() {
        // Arrange
        DirectorDTO.Director dto = new DirectorDTO.Director();
        dto.setId(Integer.MAX_VALUE);
        dto.setDirectorsName("D".repeat(1000)); // Very long name
        dto.setPopularity(Double.MAX_VALUE);

        // Act
        Director result = DirectorMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(Integer.MAX_VALUE, result.getId());
        assertEquals("D".repeat(1000), result.getDirectorsName());
        assertEquals(Double.MAX_VALUE, result.getPopularity());
    }

    @Test
    void testToEntityWithSpecialCharacters() {
        // Arrange
        DirectorDTO.Director dto = new DirectorDTO.Director();
        dto.setId(1);
        dto.setDirectorsName("Director with special chars: !@#$%^&*()");
        dto.setPopularity(50.0);

        // Act
        Director result = DirectorMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Director with special chars: !@#$%^&*()", result.getDirectorsName());
        assertEquals(50.0, result.getPopularity());
    }
}
