package com.openclassrooms.DistributeurDeBillet.Entity.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDepositDTO {
    private String name;
    private String firstName;
    private Integer depositValue;
}
