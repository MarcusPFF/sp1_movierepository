package app.entities;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AllEntitiesListsTest {

    @Test
    void testConstructor() {
        // Arrange
        List<Director> directors = Arrays.asList(
                Director.builder().directorsName("Director 1").popularity(80.0).build()
        );
        List<Actor> actors = Arrays.asList(
                Actor.builder().actorsName("Actor 1").popularity(85.0).build()
        );
        List<Movie> movies = Arrays.asList(
                Movie.builder().originalTitle("Movie 1").build()
        );
        List<MovieActorRelations> mar = Arrays.asList();
        List<MovieDirectorRelations> mdr = Arrays.asList();

        // Act
        AllEntitiesLists result = new AllEntitiesLists(directors, actors, movies, mar, mdr);

        // Assert
        assertNotNull(result);
        assertEquals(directors, result.getDirectors());
        assertEquals(actors, result.getActors());
        assertEquals(movies, result.getMovies());
        assertEquals(mar, result.getMovieActorRelations());
        assertEquals(mdr, result.getMovieDirectorRelations());
    }

    @Test
    void testConstructorWithNullLists() {
        // Act
        AllEntitiesLists result = new AllEntitiesLists(null, null, null, null, null);

        // Assert
        assertNotNull(result);
        assertNull(result.getDirectors());
        assertNull(result.getActors());
        assertNull(result.getMovies());
        assertNull(result.getMovieActorRelations());
        assertNull(result.getMovieDirectorRelations());
    }

    @Test
    void testConstructorWithEmptyLists() {
        // Arrange
        List<Director> directors = Arrays.asList();
        List<Actor> actors = Arrays.asList();
        List<Movie> movies = Arrays.asList();
        List<MovieActorRelations> mar = Arrays.asList();
        List<MovieDirectorRelations> mdr = Arrays.asList();

        // Act
        AllEntitiesLists result = new AllEntitiesLists(directors, actors, movies, mar, mdr);

        // Assert
        assertNotNull(result);
        assertTrue(result.getDirectors().isEmpty());
        assertTrue(result.getActors().isEmpty());
        assertTrue(result.getMovies().isEmpty());
        assertTrue(result.getMovieActorRelations().isEmpty());
        assertTrue(result.getMovieDirectorRelations().isEmpty());
    }

    @Test
    void testGetters() {
        // Arrange
        List<Director> directors = Arrays.asList(
                Director.builder().directorsName("Director 1").popularity(80.0).build()
        );
        List<Actor> actors = Arrays.asList(
                Actor.builder().actorsName("Actor 1").popularity(85.0).build()
        );
        List<Movie> movies = Arrays.asList(
                Movie.builder().originalTitle("Movie 1").build()
        );
        List<MovieActorRelations> mar = Arrays.asList();
        List<MovieDirectorRelations> mdr = Arrays.asList();

        AllEntitiesLists allEntitiesLists = new AllEntitiesLists(directors, actors, movies, mar, mdr);

        // Act & Assert
        assertEquals(directors, allEntitiesLists.getDirectors());
        assertEquals(actors, allEntitiesLists.getActors());
        assertEquals(movies, allEntitiesLists.getMovies());
        assertEquals(mar, allEntitiesLists.getMovieActorRelations());
        assertEquals(mdr, allEntitiesLists.getMovieDirectorRelations());
    }

    @Test
    void testConstructorWithMixedNullAndEmptyLists() {
        // Arrange
        List<Director> directors = null;
        List<Actor> actors = Arrays.asList();
        List<Movie> movies = null;
        List<MovieActorRelations> mar = Arrays.asList();
        List<MovieDirectorRelations> mdr = null;

        // Act
        AllEntitiesLists result = new AllEntitiesLists(directors, actors, movies, mar, mdr);

        // Assert
        assertNotNull(result);
        assertNull(result.getDirectors());
        assertTrue(result.getActors().isEmpty());
        assertNull(result.getMovies());
        assertTrue(result.getMovieActorRelations().isEmpty());
        assertNull(result.getMovieDirectorRelations());
    }

    @Test
    void testConstructorWithLargeLists() {
        // Arrange
        List<Director> directors = Arrays.asList(
                Director.builder().directorsName("Director 1").popularity(80.0).build(),
                Director.builder().directorsName("Director 2").popularity(90.0).build(),
                Director.builder().directorsName("Director 3").popularity(70.0).build()
        );
        List<Actor> actors = Arrays.asList(
                Actor.builder().actorsName("Actor 1").popularity(85.0).build(),
                Actor.builder().actorsName("Actor 2").popularity(95.0).build(),
                Actor.builder().actorsName("Actor 3").popularity(75.0).build()
        );
        List<Movie> movies = Arrays.asList(
                Movie.builder().originalTitle("Movie 1").build(),
                Movie.builder().originalTitle("Movie 2").build(),
                Movie.builder().originalTitle("Movie 3").build()
        );
        List<MovieActorRelations> mar = Arrays.asList();
        List<MovieDirectorRelations> mdr = Arrays.asList();

        // Act
        AllEntitiesLists result = new AllEntitiesLists(directors, actors, movies, mar, mdr);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.getDirectors().size());
        assertEquals(3, result.getActors().size());
        assertEquals(3, result.getMovies().size());
        assertTrue(result.getMovieActorRelations().isEmpty());
        assertTrue(result.getMovieDirectorRelations().isEmpty());
    }
}
