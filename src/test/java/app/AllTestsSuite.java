package app;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

/**
 * Comprehensive test suite for all classes in the application.
 * This suite includes tests for:
 * - DAO classes (MovieDAO, ActorsDAO, DirectorsDAO, MovieActorRelationsDAO, MovieDirectorRelationsDAO, DaoHandler)
 * - Mapper classes (MovieMapper, ActorMapper, DirectorMapper, GenreMapper, MovieActorRelationsMapper, MovieDirectorRelationsMapper)
 * - Service classes (ImportService, FetchTools)
 * - Utility classes (GenerateNextId, Utils)
 * - Exception classes (ApiException)
 * - Configuration classes (HibernateConfig)
 * - Entity classes (AllEntitiesLists)
 * - Main class
 */
@Suite
@SelectPackages("app")
public class AllTestsSuite {
    // This class serves as a test suite container
    // All test classes in the app package are automatically selected
}
