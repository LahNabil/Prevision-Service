package net.lahlalia.previsions.mappers;

import net.lahlalia.previsions.dtos.Bac;
import net.lahlalia.previsions.dtos.PrevisionDto;
import net.lahlalia.previsions.entities.BacItem;
import net.lahlalia.previsions.entities.Prevision;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BacMapper {
    BacItem toEntity(Bac dto);
    Bac toModel(BacItem bacItem);
}

