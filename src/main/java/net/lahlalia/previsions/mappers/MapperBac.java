package net.lahlalia.previsions.mappers;

import lombok.RequiredArgsConstructor;
import net.lahlalia.previsions.dtos.Bac;
import net.lahlalia.previsions.entities.BacItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MapperBac {
    private final ModelMapper mapper;

    public Bac convertToDto(BacItem bacItem){
        return mapper.map(bacItem, Bac.class);

    }
    public BacItem convertToModel(Bac bac){
        return mapper.map(bac, BacItem.class);

    }
}
