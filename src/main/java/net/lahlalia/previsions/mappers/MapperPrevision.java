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
        PrevisionDto previsionDto = mapper.map(prevision, PrevisionDto.class);
        return previsionDto;
    }
    public Prevision convertToModel(PrevisionDto previsionDto){
        Prevision prevision = mapper.map(previsionDto, Prevision.class);
        return prevision;
    }

}
