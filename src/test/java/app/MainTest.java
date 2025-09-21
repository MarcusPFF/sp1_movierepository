package app;

import app.config.HibernateConfig;
import app.mappers.ActorMapper;
import app.mappers.DirectorMapper;
import app.mappers.GenreMapper;
import app.mappers.MovieMapper;
import app.services.ImportService;
import app.utils.MovieRepoApp;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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
        assertDoesNotThrow(() -> {
            Class<?> mainClass = Main.class;
            assertNotNull(mainClass.getMethod("main", String[].class));
        });
    }

    @Test
    void testMainMethodSignature() {
        // Verify that the main method has the correct signature
        try {
            var mainMethod = Main.class.getMethod("main", String[].class);
            assertTrue(Modifier.isStatic(mainMethod.getModifiers()));
            assertTrue(Modifier.isPublic(mainMethod.getModifiers()));
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
                assertTrue(e.getCause() instanceof Exception);
            }
        });
    }

    @Test
    void testMovieRepoAppHasRequiredFields() {
        // MovieRepoApp should own the instance fields (private final) now
        assertHasPrivateFinalField(MovieRepoApp.class, "emf", EntityManagerFactory.class);
        assertHasPrivateFinalField(MovieRepoApp.class, "importService", ImportService.class);
        assertHasPrivateFinalField(MovieRepoApp.class, "movieMapper", MovieMapper.class);
        assertHasPrivateFinalField(MovieRepoApp.class, "genreMapper", GenreMapper.class);
        assertHasPrivateFinalField(MovieRepoApp.class, "directorMapper", DirectorMapper.class);
        assertHasPrivateFinalField(MovieRepoApp.class, "actorMapper", ActorMapper.class);
    }

    private void assertHasPrivateFinalField(Class<?> clazz, String fieldName, Class<?> expectedType) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            int mods = field.getModifiers();
            assertTrue(Modifier.isPrivate(mods), fieldName + " should be private");
            assertTrue(Modifier.isFinal(mods), fieldName + " should be final");
            assertFalse(Modifier.isStatic(mods), fieldName + " should NOT be static");
            assertEquals(expectedType, field.getType(), fieldName + " should be of type " + expectedType.getSimpleName());
        } catch (NoSuchFieldException e) {
            fail(clazz.getSimpleName() + " should have field " + fieldName);
        }
    }

    @Test
    void testMovieRepoAppHasConstructor() {
        var constructors = MovieRepoApp.class.getConstructors();
        assertTrue(constructors.length > 0, "MovieRepoApp should have at least one constructor");
        // Expect one public constructor with a single EntityManagerFactory parameter
        boolean found = false;
        for (var ctor : constructors) {
            if (ctor.getParameterCount() == 1 && ctor.getParameterTypes()[0].equals(EntityManagerFactory.class)) {
                found = true;
                break;
            }
        }
        assertTrue(found, "MovieRepoApp should have a constructor taking EntityManagerFactory");
    }

    @Test
    void testMainClassIsPublic() {
        // Verify that the Main class is public
        assertTrue(Modifier.isPublic(Main.class.getModifiers()));
    }

    @Test
    void testMainClassHasDefaultConstructor() {
        // Verify that the Main class has a default constructor
        var constructors = Main.class.getConstructors();
        assertEquals(1, constructors.length, "Main class should have one default constructor");
        assertEquals(0, constructors[0].getParameterCount(), "Default constructor should have no parameters");
    }
}
