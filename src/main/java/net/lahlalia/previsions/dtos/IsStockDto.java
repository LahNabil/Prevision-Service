package net.lahlalia.previsions.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class IsStockDto {

    private double forecaste;
    private double safetyStock;
    private double stockActuel;
    private String nameProduct;
    private String ville;
    private double creux;
    private double productCapacity;

}
