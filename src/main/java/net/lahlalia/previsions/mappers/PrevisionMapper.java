package net.lahlalia.previsions.mappers;


import net.lahlalia.previsions.dtos.PrevisionDto;
import net.lahlalia.previsions.entities.Prevision;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrevisionMapper {
    Prevision toEntity(PrevisionDto dto);
    PrevisionDto toModel(Prevision prevision);
}

