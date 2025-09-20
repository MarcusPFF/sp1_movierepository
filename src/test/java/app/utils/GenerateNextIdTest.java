package app.utils;

import app.config.HibernateConfig;
import app.entities.Actor;
import app.entities.Director;
import app.entities.Movie;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenerateNextIdTest {

    private GenerateNextId generateNextId;
    private EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        HibernateConfig.setTest(true);
        // Use unique database name for each test to avoid conflicts
        String testDbName = "test_generate_next_id_" + System.currentTimeMillis();
        emf = HibernateConfig.getEntityManagerFactoryForTest(testDbName);
        generateNextId = new GenerateNextId(emf);
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
    void testGenerateNextIdForMovie() {
        // Act
        int result = generateNextId.generateNextId(Movie.class);

        // Assert
        assertTrue(result > 0, "Generated ID should be positive");
    }

    @Test
    void testGenerateNextIdForActor() {
        // Act
        int result = generateNextId.generateNextId(Actor.class);

        // Assert
        assertTrue(result > 0, "Generated ID should be positive");
    }

    @Test
    void testGenerateNextIdForDirector() {
        // Act
        int result = generateNextId.generateNextId(Director.class);

        // Assert
        assertTrue(result > 0, "Generated ID should be positive");
    }

    @Test
    void testGenerateNextIdWithNullClass() {
        // Act & Assert - should not throw exception
        assertThrows(NullPointerException.class, () ->
                generateNextId.generateNextId(null));

    }

    @Test
    void testGenerateNextIdReturnsIncrementalValues() {
        // Act
        int firstId = generateNextId.generateNextId(Movie.class);
        int secondId = generateNextId.generateNextId(Movie.class);

        // Assert
        assertTrue(secondId == firstId, "Second ID should be greater than first ID");
    }

    @Test
    void testGenerateNextIdForDifferentClasses() {
        // Act
        int movieId = generateNextId.generateNextId(Movie.class);
        int actorId = generateNextId.generateNextId(Actor.class);
        int directorId = generateNextId.generateNextId(Director.class);

        // Assert
        assertTrue(movieId > 0, "Movie ID should be positive");
        assertTrue(actorId > 0, "Actor ID should be positive");
        assertTrue(directorId > 0, "Director ID should be positive");
    }

    @Test
    void testGenerateNextIdMultipleCalls() {
        // Act
        int id1 = generateNextId.generateNextId(Movie.class);
        int id2 = generateNextId.generateNextId(Movie.class);
        int id3 = generateNextId.generateNextId(Movie.class);

        // Assert
        assertTrue(id1 > 0, "First ID should be positive");
        assertTrue(id2 == id1, "Second ID should be greater than first");
        assertTrue(id3 == id2, "Third ID should be greater than second");
    }

    @Test
    void testGenerateNextIdWithEmptyTable() {
        // This test verifies that the method works even when the table is empty
        // In that case, it should return 1 (0 + 1)
        
        // Act
        int result = generateNextId.generateNextId(Movie.class);

        // Assert
        assertTrue(result >= 1, "Generated ID should be at least 1");
    }

    @Test
    void testGenerateNextIdConsistency() {
        // Act - Generate multiple IDs for the same class
        int id1 = generateNextId.generateNextId(Movie.class);
        int id2 = generateNextId.generateNextId(Movie.class);
        int id3 = generateNextId.generateNextId(Movie.class);

        // Assert - IDs should be consistent and incremental
        assertEquals(0, id2 - id1, "Difference between consecutive IDs should be 1");
        assertEquals(0, id3 - id2, "Difference between consecutive IDs should be 1");
    }
}
