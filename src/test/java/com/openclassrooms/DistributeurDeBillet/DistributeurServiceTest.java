package com.openclassrooms.DistributeurDeBillet;

import com.openclassrooms.DistributeurDeBillet.Entity.Customer;
import com.openclassrooms.DistributeurDeBillet.Entity.DTO.CustomerDTO;
import com.openclassrooms.DistributeurDeBillet.Entity.DTO.CustomerDepositDTO;
import com.openclassrooms.DistributeurDeBillet.Entity.DTO.CustomerWithdrawDTO;
import com.openclassrooms.DistributeurDeBillet.Entity.DTO.DistributeurRefillDTO;
import com.openclassrooms.DistributeurDeBillet.Entity.Distributeur;
import com.openclassrooms.DistributeurDeBillet.Exception.DepositException;
import com.openclassrooms.DistributeurDeBillet.Repository.CustomerRepository;
import com.openclassrooms.DistributeurDeBillet.Repository.DistributeurRepository;
import com.openclassrooms.DistributeurDeBillet.Service.CustomerService;
import com.openclassrooms.DistributeurDeBillet.Service.CustomerServiceImpl;
import com.openclassrooms.DistributeurDeBillet.Service.DistributeurServiceImpl;
import javassist.NotFoundException;
import org.aspectj.weaver.ast.Not;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.swing.text.html.Option;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;



public class DistributeurServiceTest {
    @Mock
    private DistributeurRepository distributeurRepository;
    @InjectMocks
    private DistributeurServiceImpl distributeurService;

    @Before
    public void setUp() throws Exception {
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void refill() throws NotFoundException, DepositException {
        DistributeurRefillDTO distributeurRefillDTO = new DistributeurRefillDTO();
        distributeurRefillDTO.setAutomatIdentifier("ABC");
        distributeurRefillDTO.setRefillValue(100);

        Distributeur distributeur = new Distributeur();
        distributeur.setQuantityMoneyAvailable(100);
        distributeur.setAutomatIdentifier("ABC");

        Mockito.when(distributeurRepository.findByAutomatIdentifier(any())).thenReturn(Optional.of(distributeur));
        Mockito.when(distributeurRepository.save(any())).thenReturn(any());

        Assertions.assertEquals(distributeurService.refill(distributeurRefillDTO).getAutomatIdentifier(), "ABC");
    }
}
