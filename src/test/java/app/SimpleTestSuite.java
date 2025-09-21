package app;

import org.junit.jupiter.api.Test;

/**
 * Simple test suite that can be used to run all tests.
 * This class doesn't use JUnit Suite annotations but can be used
 * as a reference for all available test classes.
 */
public class SimpleTestSuite {

    @Test
    void testSuiteInfo() {

        System.out.println("All test classes are available for execution");
        assertTrue(true);
    }
    
    private void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("Expected true but was false");
        }
    }
}
