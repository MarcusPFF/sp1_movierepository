package app.mappers;

import app.dtos.ActorDTO;
import app.entities.Actor;


public class ActorMapper {

    public static Actor toEntity(ActorDTO.Actor actor) {
        return Actor.builder()
                .id(actor.getId())
                .actorsName(actor.getActorsName())
                .popularity(actor.getPopularity())
                .build();
    }
}
