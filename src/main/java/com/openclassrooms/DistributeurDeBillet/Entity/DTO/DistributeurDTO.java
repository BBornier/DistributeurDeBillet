package com.openclassrooms.DistributeurDeBillet.Entity.DTO;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DistributeurDTO {
    private Integer quantityMoneyAvailable;
    private String automatIdentifier;
}
