package net.lahlalia.previsions.dtos;

import jakarta.persistence.*;
import lombok.*;
import net.lahlalia.previsions.entities.BacItem;
import net.lahlalia.previsions.enums.Business;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PrevisionDto {

    private Long idPrevision;
    private double foreCaste;
    private Date date;
    private Business business;
    private String nameProduct;
    private String supplyEnveloppe;
    private List<BacItem> bacItems;



}
