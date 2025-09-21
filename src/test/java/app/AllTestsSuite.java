package app;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("app")
public class AllTestsSuite {
    // This class serves as a test suite container
    // All test classes in the app package are automatically selected
}
