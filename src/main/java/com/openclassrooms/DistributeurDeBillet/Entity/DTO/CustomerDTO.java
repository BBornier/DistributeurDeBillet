package com.openclassrooms.DistributeurDeBillet.Entity.DTO;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CustomerDTO {
    private String name;
    private String firstName;
    private Integer accountBalance;
}
