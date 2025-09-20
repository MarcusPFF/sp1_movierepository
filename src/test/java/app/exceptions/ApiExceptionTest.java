package app.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiExceptionTest {

    @Test
    void testConstructorWithCodeAndMessage() {
        // Arrange
        int code = 404;
        String message = "Not Found";

        // Act
        ApiException exception = new ApiException(code, message);

        // Assert
        assertNotNull(exception);
        assertEquals(code, exception.getCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testConstructorWithZeroCode() {
        // Arrange
        int code = 0;
        String message = "Zero Code";

        // Act
        ApiException exception = new ApiException(code, message);

        // Assert
        assertNotNull(exception);
        assertEquals(0, exception.getCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testConstructorWithNegativeCode() {
        // Arrange
        int code = -1;
        String message = "Negative Code";

        // Act
        ApiException exception = new ApiException(code, message);

        // Assert
        assertNotNull(exception);
        assertEquals(-1, exception.getCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testConstructorWithNullMessage() {
        // Arrange
        int code = 500;
        String message = null;

        // Act
        ApiException exception = new ApiException(code, message);

        // Assert
        assertNotNull(exception);
        assertEquals(code, exception.getCode());
        assertNull(exception.getMessage());
    }

    @Test
    void testConstructorWithEmptyMessage() {
        // Arrange
        int code = 400;
        String message = "";

        // Act
        ApiException exception = new ApiException(code, message);

        // Assert
        assertNotNull(exception);
        assertEquals(code, exception.getCode());
        assertEquals("", exception.getMessage());
    }

    @Test
    void testConstructorWithWhitespaceMessage() {
        // Arrange
        int code = 500;
        String message = "   ";

        // Act
        ApiException exception = new ApiException(code, message);

        // Assert
        assertNotNull(exception);
        assertEquals(code, exception.getCode());
        assertEquals("   ", exception.getMessage());
    }

    @Test
    void testConstructorWithLongMessage() {
        // Arrange
        int code = 500;
        String message = "A".repeat(1000);

        // Act
        ApiException exception = new ApiException(code, message);

        // Assert
        assertNotNull(exception);
        assertEquals(code, exception.getCode());
        assertEquals("A".repeat(1000), exception.getMessage());
    }

    @Test
    void testConstructorWithSpecialCharactersInMessage() {
        // Arrange
        int code = 500;
        String message = "Error with special chars: !@#$%^&*()";

        // Act
        ApiException exception = new ApiException(code, message);

        // Assert
        assertNotNull(exception);
        assertEquals(code, exception.getCode());
        assertEquals("Error with special chars: !@#$%^&*()", exception.getMessage());
    }

    @Test
    void testConstructorWithUnicodeCharactersInMessage() {
        // Arrange
        int code = 500;
        String message = "Error with unicode: 中文 日本語 한국어";

        // Act
        ApiException exception = new ApiException(code, message);

        // Assert
        assertNotNull(exception);
        assertEquals(code, exception.getCode());
        assertEquals("Error with unicode: 中文 日本語 한국어", exception.getMessage());
    }

    @Test
    void testGetCode() {
        // Arrange
        int code = 200;
        String message = "Success";

        // Act
        ApiException exception = new ApiException(code, message);

        // Assert
        assertEquals(code, exception.getCode());
    }

    @Test
    void testGetMessage() {
        // Arrange
        int code = 500;
        String message = "Internal Server Error";

        // Act
        ApiException exception = new ApiException(code, message);

        // Assert
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testInheritanceFromRuntimeException() {
        // Arrange
        int code = 500;
        String message = "Test Exception";

        // Act
        ApiException exception = new ApiException(code, message);

        // Assert
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testExceptionCanBeThrown() {
        // Arrange
        int code = 500;
        String message = "Test Exception";

        // Act & Assert
        assertThrows(ApiException.class, () -> {
            throw new ApiException(code, message);
        });
    }

    @Test
    void testExceptionCanBeCaught() {
        // Arrange
        int code = 500;
        String message = "Test Exception";

        // Act
        try {
            throw new ApiException(code, message);
        } catch (ApiException e) {
            // Assert
            assertEquals(code, e.getCode());
            assertEquals(message, e.getMessage());
        }
    }
}
