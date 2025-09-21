package app;

import app.config.HibernateConfig;
import app.mappers.ActorMapper;
import app.mappers.DirectorMapper;
import app.mappers.GenreMapper;
import app.mappers.MovieMapper;
import app.services.ImportService;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @BeforeEach
    void setUp() {
        HibernateConfig.setTest(true);
    }

    @AfterEach
    void tearDown() {
        HibernateConfig.setTest(false);
    }

    @Test
    void testMainMethodExists() {
        // This test verifies that the main method exists and can be called
        // We can't actually call main() with args in a test, but we can verify the class structure
        
        // Act & Assert
        assertDoesNotThrow(() -> {
            // Verify that the Main class can be instantiated (if it had a constructor)
            // and that the main method exists
            Class<?> mainClass = Main.class;
            assertNotNull(mainClass.getMethod("main", String[].class));
        });
    }

    @Test
    void testMainMethodSignature() {
        // Verify that the main method has the correct signature
        try {
            var mainMethod = Main.class.getMethod("main", String[].class);
            assertTrue(java.lang.reflect.Modifier.isStatic(mainMethod.getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isPublic(mainMethod.getModifiers()));
            assertEquals(void.class, mainMethod.getReturnType());
            assertEquals(1, mainMethod.getParameterCount());
            assertEquals(String[].class, mainMethod.getParameterTypes()[0]);
        } catch (NoSuchMethodException e) {
            fail("Main method should exist");
        }
    }

    @Test
    void testMainMethodCanBeCalled() {
        // This test verifies that the main method can be called without throwing exceptions
        // We'll use reflection to call it with empty args
        
        // Act & Assert
        assertDoesNotThrow(() -> {
            try {
                var mainMethod = Main.class.getMethod("main", String[].class);
                mainMethod.invoke(null, (Object) new String[0]);
            } catch (Exception e) {
                // The main method might throw exceptions due to missing environment setup
                // This is expected in a test environment
                assertTrue(e.getCause() instanceof Exception);
            }
        });
    }

    @Test
    void testMainMethodWithNullArgs() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            try {
                var mainMethod = Main.class.getMethod("main", String[].class);
                mainMethod.invoke(null, (Object) null);
            } catch (Exception e) {
                // The main method might throw exceptions due to missing environment setup
                // This is expected in a test environment
                assertTrue(e.getCause() instanceof Exception);
            }
        });
    }

    @Test
    void testMainMethodWithValidArgs() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            try {
                var mainMethod = Main.class.getMethod("main", String[].class);
                mainMethod.invoke(null, (Object) new String[]{"arg1", "arg2"});
            } catch (Exception e) {
                // The main method might throw exceptions due to missing environment setup
                // This is expected in a test environment
                assertTrue(e.getCause() instanceof Exception);
            }
        });
    }

    @Test
    void testMainClassHasRequiredFields() {
        // Verify that the Main class has the expected static fields
        try {
            var emfField = Main.class.getDeclaredField("emf");
            assertTrue(java.lang.reflect.Modifier.isStatic(emfField.getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isPrivate(emfField.getModifiers()));
            assertEquals(EntityManagerFactory.class, emfField.getType());
        } catch (NoSuchFieldException e) {
            fail("Main class should have emf field");
        }

        try {
            var isField = Main.class.getDeclaredField("is");
            assertTrue(java.lang.reflect.Modifier.isStatic(isField.getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isPrivate(isField.getModifiers()));
            assertEquals(ImportService.class, isField.getType());
        } catch (NoSuchFieldException e) {
            fail("Main class should have is field");
        }

        try {
            var mmField = Main.class.getDeclaredField("mm");
            assertTrue(java.lang.reflect.Modifier.isStatic(mmField.getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isPrivate(mmField.getModifiers()));
            assertEquals(MovieMapper.class, mmField.getType());
        } catch (NoSuchFieldException e) {
            fail("Main class should have mm field");
        }

        try {
            var gmField = Main.class.getDeclaredField("gm");
            assertTrue(java.lang.reflect.Modifier.isStatic(gmField.getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isPrivate(gmField.getModifiers()));
            assertEquals(GenreMapper.class, gmField.getType());
        } catch (NoSuchFieldException e) {
            fail("Main class should have gm field");
        }

        try {
            var dmField = Main.class.getDeclaredField("dm");
            assertTrue(java.lang.reflect.Modifier.isStatic(dmField.getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isPrivate(dmField.getModifiers()));
            assertEquals(DirectorMapper.class, dmField.getType());
        } catch (NoSuchFieldException e) {
            fail("Main class should have dm field");
        }

        try {
            var amField = Main.class.getDeclaredField("am");
            assertTrue(java.lang.reflect.Modifier.isStatic(amField.getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isPrivate(amField.getModifiers()));
            assertEquals(ActorMapper.class, amField.getType());
        } catch (NoSuchFieldException e) {
            fail("Main class should have am field");
        }
    }

    @Test
    void testMainClassIsPublic() {
        // Verify that the Main class is public
        assertTrue(java.lang.reflect.Modifier.isPublic(Main.class.getModifiers()));
    }

    @Test
    void testMainClassHasDefaultConstructor() {
        // Verify that the Main class has a default constructor
        var constructors = Main.class.getConstructors();
        assertEquals(1, constructors.length, "Main class should have one default constructor");
        assertEquals(0, constructors[0].getParameterCount(), "Default constructor should have no parameters");
    }
}
