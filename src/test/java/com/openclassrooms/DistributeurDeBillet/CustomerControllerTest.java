//package com.openclassrooms.DistributeurDeBillet;
//
//import com.openclassrooms.DistributeurDeBillet.Controller.CustomerController;
//import com.openclassrooms.DistributeurDeBillet.Controller.DistributeurController;
//import com.openclassrooms.DistributeurDeBillet.Entity.DTO.CustomerDepositDTO;
//import com.openclassrooms.DistributeurDeBillet.Repository.CustomerRepository;
//import com.openclassrooms.DistributeurDeBillet.Repository.DistributeurRepository;
//import com.openclassrooms.DistributeurDeBillet.Service.CustomerService;
//import com.openclassrooms.DistributeurDeBillet.Service.DistributeurService;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//
//public class CustomerControllerTest {
//    @Mock
//    private CustomerRepository customerRepository;
//    @Mock
//    private CustomerService customerService;
//    @InjectMocks
//    private CustomerController customerController;
//
//    @Before
//    public void setUp() throws Exception {
//    }
//
//    @BeforeEach
//    public void init(){
//        MockitoAnnotations.openMocks(this);
//    }
//
//
//    @Test
//    public void validDeposit(){
//        CustomerDepositDTO customerDepositDTO = new CustomerDepositDTO();
//        customerDepositDTO.setDepositValue(100);
//        customerDepositDTO.setFirstName("Jean");
//        customerDepositDTO.setName("Alex");
//
//        ResponseEntity<Object> ret = ResponseEntity.ok().body("");
//
//        Assertions.assertEquals(customerController.deposit(customerDepositDTO).getStatusCode(), ret.getStatusCode());
//    }
//}
