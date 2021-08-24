package com.openclassrooms.DistributeurDeBillet;

import com.openclassrooms.DistributeurDeBillet.Controller.CustomerController;
import com.openclassrooms.DistributeurDeBillet.Controller.DistributeurController;
import com.openclassrooms.DistributeurDeBillet.Entity.Customer;
import com.openclassrooms.DistributeurDeBillet.Entity.DTO.CustomerWithdrawDTO;
import com.openclassrooms.DistributeurDeBillet.Entity.DTO.DistributeurRefillDTO;
import com.openclassrooms.DistributeurDeBillet.Entity.Distributeur;
import com.openclassrooms.DistributeurDeBillet.Exception.DepositException;
import com.openclassrooms.DistributeurDeBillet.Repository.CustomerRepository;
import com.openclassrooms.DistributeurDeBillet.Repository.DistributeurRepository;
import com.openclassrooms.DistributeurDeBillet.Service.CustomerServiceImpl;
import com.openclassrooms.DistributeurDeBillet.Service.DistributeurService;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import lombok.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

public class DistributeurControllerTest {
    @Mock
    private DistributeurRepository distributeurRepository;
    @Mock
    private DistributeurService distributeurService;
    @InjectMocks
    private DistributeurController distributeurController;

    @Before
    public void setUp() throws Exception {
    }

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void withdrawNotEnoughMoneyInDistributeur(){
        DistributeurRefillDTO distributeurRefillDTO = new DistributeurRefillDTO();
        distributeurRefillDTO.setRefillValue(100);
        distributeurRefillDTO.setAutomatIdentifier("100");

        Distributeur distributeur = new Distributeur();
        distributeur.setQuantityMoneyAvailable(100);

        Mockito.when(distributeurRepository.findByAutomatIdentifier(any())).thenReturn(Optional.of(distributeur));
        Mockito.when(distributeurRepository.save(any())).thenReturn(distributeur);

        ResponseEntity<Object> ret = ResponseEntity.ok().body("");

        Assertions.assertEquals(distributeurController.refill(distributeurRefillDTO).getStatusCode(), ret.getStatusCode());
    }

    @Test
    public void refillWithBadValue() throws NotFoundException, DepositException {
        DistributeurRefillDTO distributeurRefillDTO = new DistributeurRefillDTO();
        distributeurRefillDTO.setRefillValue(101);
        distributeurRefillDTO.setAutomatIdentifier("100");

        Distributeur distributeur = new Distributeur();
        distributeur.setQuantityMoneyAvailable(101);

        Mockito.when(distributeurService.refill(any())).thenThrow(DepositException.class);
        Mockito.when(distributeurRepository.findByAutomatIdentifier(any())).thenReturn(Optional.of(distributeur));
        Mockito.when(distributeurRepository.save(any())).thenReturn(distributeur);

        ResponseEntity<Object> ret = ResponseEntity.badRequest().body("");

        Assertions.assertEquals(distributeurController.refill(distributeurRefillDTO).getStatusCode(), ret.getStatusCode());
    }

    @Test
    public void refillNotFound() throws NotFoundException, DepositException {
        DistributeurRefillDTO distributeurRefillDTO = new DistributeurRefillDTO();
        distributeurRefillDTO.setRefillValue(101);
        distributeurRefillDTO.setAutomatIdentifier("100");

        Distributeur distributeur = new Distributeur();
        distributeur.setQuantityMoneyAvailable(101);

        Mockito.when(distributeurService.refill(any())).thenThrow(NotFoundException.class);
        Mockito.when(distributeurRepository.findByAutomatIdentifier(any())).thenReturn(Optional.of(distributeur));
        Mockito.when(distributeurRepository.save(any())).thenReturn(distributeur);

        ResponseEntity<Object> ret = ResponseEntity.badRequest().body("");

        Assertions.assertEquals(distributeurController.refill(distributeurRefillDTO).getStatusCode(), ret.getStatusCode());
    }
}
