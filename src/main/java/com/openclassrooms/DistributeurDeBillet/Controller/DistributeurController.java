package com.openclassrooms.DistributeurDeBillet.Controller;

import com.openclassrooms.DistributeurDeBillet.Entity.Customer;
import com.openclassrooms.DistributeurDeBillet.Entity.DTO.CustomerDTO;
import com.openclassrooms.DistributeurDeBillet.Entity.DTO.CustomerDepositDTO;
import com.openclassrooms.DistributeurDeBillet.Entity.DTO.DistributeurRefillDTO;
import com.openclassrooms.DistributeurDeBillet.Exception.DepositException;
import com.openclassrooms.DistributeurDeBillet.Service.DistributeurService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/distributeur")
@AllArgsConstructor
public class DistributeurController {
    private final DistributeurService distributeurService;

    @PostMapping("/refill")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Object> refill(@RequestBody DistributeurRefillDTO distributeurRefillDTO){
        try{
            return ResponseEntity.ok(distributeurService.refill(distributeurRefillDTO));
        }catch(NotFoundException e){
            return ResponseEntity.badRequest().body("Automate identifier is invalid");
        }catch(DepositException depositException){
            return ResponseEntity.badRequest().body("Deposit refill isn't a multiple of 10");
        }
    }
}
