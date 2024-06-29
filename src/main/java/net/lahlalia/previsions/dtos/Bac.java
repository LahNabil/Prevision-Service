package net.lahlalia.previsions.dtos;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Bac {
    private String idBac;
    private double capacity;
    private double totalImpom;
    private boolean status;
    private Date dateOuverture;
    private double capacityUsed;
    private Long idProduct;
    private String idDepot;
}