# Test Suite Documentation

This document describes the comprehensive test suite created for the Movie Repository application.

## Overview

The test suite includes tests for all methods in every class of the application, ensuring comprehensive coverage without modifying any existing code.

## Test Structure

### Test Dependencies

The following dependencies have been added to `pom.xml` for testing:

- **JUnit 5** (junit-jupiter): Core testing framework
- **H2 Database**: In-memory database for testing
- **Mockito**: Mocking framework for unit tests

### Test Configuration

- **Hibernate Test Configuration**: `src/test/resources/hibernate-test.cfg.xml`
- **Test Properties**: `src/test/resources/config.properties`
- **Test Database**: H2 in-memory database for isolated testing

## Test Classes

### DAO Tests (`src/test/java/app/daos/`)

1. **MovieDAOTest.java**
   - Tests all CRUD operations for Movie entity
   - Tests genre management and relationships
   - Tests error handling and edge cases

2. **ActorsDAOTest.java**
   - Tests all CRUD operations for Actor entity
   - Tests bulk operations and list management
   - Tests error handling scenarios

3. **DirectorsDAOTest.java**
   - Tests all CRUD operations for Director entity
   - Tests bulk operations and list management
   - Tests error handling scenarios

4. **MovieActorRelationsDAOTest.java**
   - Tests relationship management between Movie and Actor entities
   - Tests complex relationship operations
   - Tests error handling for relationship operations

5. **MovieDirectorRelationsDAOTest.java**
   - Tests relationship management between Movie and Director entities
   - Tests complex relationship operations
   - Tests error handling for relationship operations

6. **DaoHandlerTest.java**
   - Tests the orchestration of multiple DAO operations
   - Tests database operation execution
   - Tests special actor addition functionality

### Mapper Tests (`src/test/java/app/mappers/`)

1. **MovieMapperTest.java**
   - Tests DTO to Entity conversion
   - Tests all query methods (top rated, popular, genre-based, etc.)
   - Tests edge cases and error handling

2. **ActorMapperTest.java**
   - Tests DTO to Entity conversion
   - Tests actor listing functionality
   - Tests various data scenarios

3. **DirectorMapperTest.java**
   - Tests DTO to Entity conversion
   - Tests director listing functionality
   - Tests various data scenarios

4. **GenreMapperTest.java**
   - Tests DTO to Entity conversion
   - Tests genre listing functionality
   - Tests duplicate removal and sorting

5. **MovieActorRelationsMapperTest.java**
   - Tests relationship entity creation
   - Tests various parameter combinations
   - Tests edge cases and null handling

6. **MovieDirectorRelationsMapperTest.java**
   - Tests relationship entity creation
   - Tests various parameter combinations
   - Tests edge cases and null handling

### Service Tests (`src/test/java/app/services/`)

1. **ImportServiceTest.java**
   - Tests movie service orchestration
   - Tests data processing workflows
   - Tests error handling and edge cases

2. **FetchToolsTest.java**
   - Tests API integration methods
   - Tests data fetching functionality
   - Tests error handling for external API calls

### Utility Tests (`src/test/java/app/utils/`)

1. **GenerateNextIdTest.java**
   - Tests ID generation functionality
   - Tests different entity types
   - Tests incremental ID generation

2. **UtilsTest.java**
   - Tests property file reading
   - Tests error handling for missing properties
   - Tests various property scenarios

### Exception Tests (`src/test/java/app/exceptions/`)

1. **ApiExceptionTest.java**
   - Tests custom exception creation
   - Tests exception properties and methods
   - Tests various exception scenarios

### Configuration Tests (`src/test/java/app/config/`)

1. **HibernateConfigTest.java**
   - Tests Hibernate configuration management
   - Tests EntityManagerFactory creation
   - Tests test vs production configuration

### Entity Tests (`src/test/java/app/entities/`)

1. **AllEntitiesListsTest.java**
   - Tests entity list container functionality
   - Tests constructor and getter methods
   - Tests various data scenarios

### Main Class Tests (`src/test/java/app/`)

1. **MainTest.java**
   - Tests main method existence and signature
   - Tests class structure and fields
   - Tests method accessibility

## Test Suite Runner

**AllTestsSuite.java** - A comprehensive test suite that runs all test classes in the correct order.

## Running Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=MovieDAOTest
```

### Run Test Suite
```bash
mvn test -Dtest=AllTestsSuite
```

### Run with Coverage
```bash
mvn test jacoco:report
```

## Test Features

### Comprehensive Coverage
- Every public method in every class is tested
- Edge cases and error scenarios are covered
- Null handling and boundary conditions are tested

### Isolation
- Each test is independent
- H2 in-memory database ensures test isolation
- No external dependencies required

### Mocking
- External API calls are mocked where necessary
- Database operations are tested with real H2 database
- Service dependencies are properly mocked

### Error Handling
- Exception scenarios are thoroughly tested
- Error messages and codes are verified
- Graceful degradation is tested

## Test Data

- Tests use synthetic data created within the test methods
- No external data files are required
- Test data is isolated and doesn't affect production data

## Best Practices

1. **Arrange-Act-Assert Pattern**: All tests follow the AAA pattern
2. **Descriptive Test Names**: Test method names clearly describe what is being tested
3. **Single Responsibility**: Each test method tests one specific behavior
4. **Independent Tests**: Tests don't depend on each other
5. **Clean Setup/Teardown**: Proper resource management in test lifecycle

## Notes

- All tests are designed to run without modifying existing production code
- Tests use the same Hibernate configuration as production but with H2 database
- Mock objects are used for external dependencies
- Test database is created fresh for each test run
- All tests are designed to be fast and reliable
