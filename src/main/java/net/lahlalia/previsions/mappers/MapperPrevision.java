package net.lahlalia.previsions.mappers;

import lombok.RequiredArgsConstructor;
import net.lahlalia.previsions.dtos.PrevisionDto;
import net.lahlalia.previsions.entities.Prevision;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapperPrevision {
    private final ModelMapper mapper;

    public PrevisionDto convertToDto(Prevision prevision){
        return mapper.map(prevision, PrevisionDto.class);

    }
    public Prevision convertToModel(PrevisionDto previsionDto){
        return mapper.map(previsionDto, Prevision.class);

    }

}
