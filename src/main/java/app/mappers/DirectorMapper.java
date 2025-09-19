package app.mappers;

import app.dtos.DirectorDTO;
import app.entities.Director;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;


public class DirectorMapper {

    public static Director toEntity(DirectorDTO.Director director) {
        return Director.builder()
                .id(director.getId())
                .directorsName(director.getDirectorsName())
                .popularity(director.getPopularity())
                .build();
    }

    public List<Director> listOfAllDirectors(EntityManagerFactory emf) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT DISTINCT d FROM Movie m " + "JOIN m.movieDirectorRelations mdr " + "JOIN mdr.director d " + "ORDER BY d.directorsName", Director.class)
                    .getResultList();
        }
    }

}
