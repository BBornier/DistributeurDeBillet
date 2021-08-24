package com.openclassrooms.DistributeurDeBillet.Entity.DTO;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DistributeurRefillDTO {
    private String automatIdentifier;
    private Integer refillValue;
}
