package com.openclassrooms.DistributeurDeBillet.Entity.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerWithdrawDTO {
    private String name;
    private String firstName;
    private Integer withdrawValue;
    private String automatIdentifier;
}
