package app.utils;

import app.exceptions.ApiException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {



    @Test
    void testGetPropertyValueWithNonExistentProperty() {
        // Arrange
        String propName = "non.existent.property";
        String resourceName = "config.properties";

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            Utils.getPropertyValue(propName, resourceName);
        });

        assertEquals(500, exception.getCode());
        assertTrue(exception.getMessage().contains("Property non.existent.property not found"));
    }

    @Test
    void testGetPropertyValueWithNonExistentResource() {
        // Arrange
        String propName = "any.property";
        String resourceName = "non-existent.properties";

        // Act & Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            Utils.getPropertyValue(propName, resourceName);
        });

        assertTrue(exception.getMessage().contains("inStream parameter is null"));
    }

    @Test
    void testGetPropertyValueWithNullPropertyName() {
        // Arrange
        String propName = null;
        String resourceName = "config.properties";

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            Utils.getPropertyValue(propName, resourceName);
        });
    }

    @Test
    void testGetPropertyValueWithNullResourceName() {
        // Arrange
        String propName = "test.property";
        String resourceName = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            Utils.getPropertyValue(propName, resourceName);
        });
    }

    @Test
    void testGetPropertyValueWithEmptyPropertyName() {
        // Arrange
        String propName = "";
        String resourceName = "config.properties";

        // Act & Assert
        assertThrows(ApiException.class, () ->
                Utils.getPropertyValue(propName, resourceName)
        );
    }

    @Test
    void testGetPropertyValueWithEmptyResourceName() {
        // Arrange
        String propName = "test.property";
        String resourceName = "";

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            Utils.getPropertyValue(propName, resourceName);
        });

        assertEquals(500, exception.getCode());
        System.out.println(exception.getMessage() + 5);
        assertTrue(exception.getMessage().contains("test.property not found"));
    }


    @Test
    void testGetPropertyValueWithUnicodeCharacters() {
        // Arrange
        String propName = "test.property.with.unicode.中文";
        String resourceName = "config.properties";

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            Utils.getPropertyValue(propName, resourceName);
        });

        assertEquals(500, exception.getCode());
        assertTrue(exception.getMessage().contains("Property test.property.with.unicode.中文 not found in config.properties"));
    }
}
