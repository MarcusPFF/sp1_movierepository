package app.mappers;

import app.dtos.ActorDTO;
import app.entities.Actor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;


public class ActorMapper {

    public static Actor toEntity(ActorDTO.Actor actor) {
        return Actor.builder()
                .id(actor.getId())
                .actorsName(actor.getActorsName())
                .popularity(actor.getPopularity())
                .build();
    }

    public List<Actor> listOfAllActors(EntityManagerFactory emf) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT DISTINCT a FROM Movie m " + "JOIN m.movieActorRelations mar " + "JOIN mar.actor a " + "ORDER BY a.actorsName", Actor.class).getResultList();
        }
    }
}
