package app.daos;

import app.entities.*;

import java.util.List;

public class DaoHandler {
    private MovieDAO movieDAO;
    private ActorsDAO actorsDAO;
    private DirectorsDAO directorsDAO;
    private MovieActorRelationsDAO movieActorRelationsDAO;
    private MovieDirectorRelationsDAO movieDirectorRelationsDAO;
    private AllEntitiesLists allEntitiesLists;

    public DaoHandler(MovieDAO movieDAO, ActorsDAO actorsDAO, DirectorsDAO directorsDAO, MovieActorRelationsDAO movieActorRelationsDAO, MovieDirectorRelationsDAO movieDirectorRelationsDAO, AllEntitiesLists allEntitiesLists) {
        this.movieDAO = movieDAO;
        this.actorsDAO = actorsDAO;
        this.directorsDAO = directorsDAO;
        this.movieActorRelationsDAO = movieActorRelationsDAO;
        this.movieDirectorRelationsDAO = movieDirectorRelationsDAO;
        this.allEntitiesLists = allEntitiesLists;
    }

    public void executeDatabaseOperations() {
        if (allEntitiesLists == null)
            return;

        List<Movie> movies = allEntitiesLists.getMovies();
        List<Actor> actors = allEntitiesLists.getActors();
        List<Director> directors = allEntitiesLists.getDirectors();
        List<MovieActorRelations> mar = allEntitiesLists.getMovieActorRelations();
        List<MovieDirectorRelations> mdr = allEntitiesLists.getMovieDirectorRelations();

        if (!actors.isEmpty()) actorsDAO.create(actors);
        if (!directors.isEmpty()) directorsDAO.create(directors);
        if (!movies.isEmpty()) movieDAO.create(movies);
        if (!mar.isEmpty()) movieActorRelationsDAO.create(mar);
        if (!mdr.isEmpty()) movieDirectorRelationsDAO.create(mdr);
    }
}
