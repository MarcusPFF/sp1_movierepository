package app.daos;

import app.config.HibernateConfig;
import app.entities.Director;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DirectorsDAOTest {

    private DirectorsDAO directorsDAO;
    private EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        HibernateConfig.setTest(true);
        // Use unique database name for each test to avoid conflicts
        String testDbName = "test_directors_" + System.currentTimeMillis();
        emf = HibernateConfig.getEntityManagerFactoryForTest(testDbName);
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
    void testCreateSingleDirector() {
        // Arrange
        Director director = Director.builder()
                .directorsName("Test Director")
                .popularity(85.5)
                .build();

        // Act
        Director result = directorsDAO.create(director);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Test Director", result.getDirectorsName());
        assertEquals(85.5, result.getPopularity());
    }

    @Test
    void testCreateMultipleDirectors() {
        // Arrange
        Director director1 = Director.builder()
                .id(1)
                .directorsName("Director 1")
                .popularity(80.0)
                .build();

        Director director2 = Director.builder()
                .id(2)
                .directorsName("Director 2")
                .popularity(90.0)
                .build();

        List<Director> directors = Arrays.asList(director1, director2);

        // Act
        List<Director> result = directorsDAO.create(directors);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertNotNull(result.get(0).getId());
        assertNotNull(result.get(1).getId());
    }

    @Test
    void testGetById() {
        // Arrange
        Director director = Director.builder()
                .directorsName("Test Director")
                .popularity(85.5)
                .build();

        directorsDAO.create(director);
        Integer directorId = director.getId();

        // Act
        Director result = directorsDAO.getById(directorId);

        // Assert
        assertNotNull(result);
        assertEquals(directorId, result.getId());
        assertEquals("Test Director", result.getDirectorsName());
        assertEquals(85.5, result.getPopularity());
    }

    @Test
    void testGetByIdNotFound() {
        // Act
        Director result = directorsDAO.getById(99999);

        // Assert
        assertNull(result);
    }

    @Test
    void testGetAll() {
        // Arrange
        Director director1 = Director.builder()
                .directorsName("Director 1")
                .popularity(80.0)
                .build();

        Director director2 = Director.builder()
                .directorsName("Director 2")
                .popularity(90.0)
                .build();

        directorsDAO.create(director1);
        directorsDAO.create(director2);

        // Act
        List<Director> result = directorsDAO.getAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.size() >= 2);
    }

    @Test
    void testUpdate() {
        // Arrange
        Director director = Director.builder()
                .directorsName("Original Name")
                .popularity(75.0)
                .build();

        directorsDAO.create(director);
        Integer directorId = director.getId();

        // Update the director
        director.setDirectorsName("Updated Name");
        director.setPopularity(95.0);

        // Act
        Director result = directorsDAO.update(director);

        // Assert
        assertNotNull(result);
        assertEquals(directorId, result.getId());
        assertEquals("Updated Name", result.getDirectorsName());
        assertEquals(95.0, result.getPopularity());
    }

    @Test
    void testUpdateNonExistentDirector() {
        // Arrange
        Director director = Director.builder()
                .id(99999)
                .directorsName("Non-existent Director")
                .popularity(75.0)
                .build();

        // Act
        Director result = directorsDAO.update(director);

        // Assert
        assertNull(result);
    }

    @Test
    void testDelete() {
        // Arrange
        Director director = Director.builder()
                .directorsName("Director to Delete")
                .popularity(75.0)
                .build();

        directorsDAO.create(director);
        Integer directorId = director.getId();

        // Act
        boolean result = directorsDAO.delete(directorId);

        // Assert
        assertTrue(result);
        assertNull(directorsDAO.getById(directorId));
    }

    @Test
    void testDeleteNonExistentDirector() {
        // Act
        boolean result = directorsDAO.delete(99999);

        // Assert
        assertFalse(result);
    }

    @Test
    void testCreateWithEmptyList() {
        // Arrange
        List<Director> emptyList = Arrays.asList();

        // Act
        List<Director> result = directorsDAO.create(emptyList);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testCreateWithNullValues() {
        // Arrange
        Director director = Director.builder()
                .directorsName(null)
                .popularity(0)
                .build();

        // Act & Assert - should not throw exception
        assertDoesNotThrow(() -> directorsDAO.create(director));
    }
}
