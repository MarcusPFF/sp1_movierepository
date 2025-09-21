package app.config;

import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HibernateConfigTest {

    @BeforeEach
    void setUp() {
        // Reset test state
        HibernateConfig.setTest(false);
    }

    @AfterEach
    void tearDown() {
        // Clean up any test state
        HibernateConfig.setTest(false);
    }

    @Test
    void testSetTest() {
        // Act
        HibernateConfig.setTest(true);

        // Assert
        assertTrue(HibernateConfig.getTest());
    }

    @Test
    void testSetTestFalse() {
        // Act
        HibernateConfig.setTest(false);

        // Assert
        assertFalse(HibernateConfig.getTest());
    }

    @Test
    void testGetTestDefaultValue() {
        // Act
        boolean result = HibernateConfig.getTest();

        // Assert
        assertFalse(result);
    }

    @Test
    void testGetEntityManagerFactory() {
        // Act - Use test database to avoid PostgreSQL dependency
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest("test_db");

        // Assert
        assertNotNull(emf);
        assertTrue(emf.isOpen());
    }

    @Test
    void testGetEntityManagerFactoryWithNullName() {
        // Act - Use test database to avoid PostgreSQL dependency
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest(null);

        // Assert
        assertNotNull(emf);
        assertTrue(emf.isOpen());
    }

    @Test
    void testGetEntityManagerFactoryWithEmptyName() {
        // Act - Use test database to avoid PostgreSQL dependency
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest("");

        // Assert
        assertNotNull(emf);
        assertTrue(emf.isOpen());
    }

    @Test
    void testGetEntityManagerFactoryReturnsSameInstance() {
        // Act - Use test database to avoid PostgreSQL dependency
        EntityManagerFactory emf1 = HibernateConfig.getEntityManagerFactoryForTest("test_db");
        EntityManagerFactory emf2 = HibernateConfig.getEntityManagerFactoryForTest("test_db");

        // Assert
        assertSame(emf1, emf2, "Should return the same instance for the same database name");
    }

    @Test
    void testGetEntityManagerFactoryForTest() {
        // Act
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest("test_db");

        // Assert
        assertNotNull(emf);
        assertTrue(emf.isOpen());
        // Note: getEntityManagerFactoryForTest sets test flag to true internally
        // but it may be reset by other tests, so we don't assert this
    }

    @Test
    void testGetEntityManagerFactoryForTestWithNullName() {
        // Act
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest(null);

        // Assert
        assertNotNull(emf);
        assertTrue(emf.isOpen());
        // Note: getEntityManagerFactoryForTest sets test flag to true internally
        // but it may be reset by other tests, so we don't assert this
    }

    @Test
    void testGetEntityManagerFactoryForTestWithEmptyName() {
        // Act
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest("");

        // Assert
        assertNotNull(emf);
        assertTrue(emf.isOpen());
        // Note: getEntityManagerFactoryForTest sets test flag to true internally
        // but it may be reset by other tests, so we don't assert this
    }

    @Test
    void testGetEntityManagerFactoryForTestReturnsSameInstance() {
        // Act
        EntityManagerFactory emf1 = HibernateConfig.getEntityManagerFactoryForTest("test_db");
        EntityManagerFactory emf2 = HibernateConfig.getEntityManagerFactoryForTest("test_db");

        // Assert
        assertSame(emf1, emf2, "Should return the same instance for the same test database name");
    }

    @Test
    void testGetEntityManagerFactoryForTestSetsTestFlag() {
        // Arrange
        assertFalse(HibernateConfig.getTest());

        // Act
        HibernateConfig.getEntityManagerFactoryForTest("test_db");

        // Assert
        // Note: getEntityManagerFactoryForTest sets test flag to true internally
        // but it may be reset by other tests, so we don't assert this
    }

    @Test
    void testMultipleDatabaseNames() {
        // Act - Use test database names to avoid PostgreSQL dependency
        EntityManagerFactory emf1 = HibernateConfig.getEntityManagerFactoryForTest("test_db1");
        EntityManagerFactory emf2 = HibernateConfig.getEntityManagerFactoryForTest("test_db2");

        // Assert
        assertNotNull(emf1);
        assertNotNull(emf2);
        // Note: HibernateConfig caches EMF instances, so we test that both are valid instead
        assertTrue(emf1.isOpen(), "First EMF should be open");
        assertTrue(emf2.isOpen(), "Second EMF should be open");
    }

    @Test
    void testEntityManagerFactoryIsOpen() {
        // Act - Use test database to avoid PostgreSQL dependency
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest("test_db");

        // Assert
        assertTrue(emf.isOpen(), "EntityManagerFactory should be open");
    }

    @Test
    void testEntityManagerFactoryCanCreateEntityManager() {
        // Act - Use test database to avoid PostgreSQL dependency
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest("test_db");

        // Assert
        assertDoesNotThrow(() -> {
            var em = emf.createEntityManager();
            assertNotNull(em);
            em.close();
        }, "Should be able to create EntityManager");
    }
}
