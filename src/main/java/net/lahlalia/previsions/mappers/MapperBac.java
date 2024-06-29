package net.lahlalia.previsions.mappers;

import lombok.RequiredArgsConstructor;
import net.lahlalia.previsions.dtos.Bac;
import net.lahlalia.previsions.dtos.PrevisionDto;
import net.lahlalia.previsions.entities.BacItem;
import net.lahlalia.previsions.entities.Prevision;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MapperBac {
    private final ModelMapper mapper;

    public Bac convertToDto(BacItem bacItem){
        Bac bac = mapper.map(bacItem, Bac.class);
        return bac;
    }
    public BacItem convertToModel(Bac bac){
        BacItem bacItem = mapper.map(bac, BacItem.class);
        return bacItem;
    }
}
