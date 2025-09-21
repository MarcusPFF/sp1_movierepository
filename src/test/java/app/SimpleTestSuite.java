package app;

import org.junit.jupiter.api.Test;


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
