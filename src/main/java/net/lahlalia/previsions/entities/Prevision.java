package net.lahlalia.previsions.entities;
import jakarta.persistence.*;
import lombok.*;
import net.lahlalia.previsions.enums.Business;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Prevision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrevision;
    private double foreCaste;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date date;
    @Enumerated(EnumType.STRING)
    private Business business;
    private String nameProduct;
    private String supplyEnveloppe;
    @OneToMany(mappedBy = "prevision")
    private List<BacItem> bacItems;




}

