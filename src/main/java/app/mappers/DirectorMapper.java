package app.mappers;

import app.dtos.ActorDTO;
import app.dtos.DirectorDTO;
import app.entities.Director;


public class DirectorMapper {

    public static Director toEntity(DirectorDTO.Director director) {
        return Director.builder()
                .id(director.getId())
                .directorsName(director.getDirectorsName())
                .popularity(director.getPopularity())
                .build();
    }
}
