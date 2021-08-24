package com.openclassrooms.DistributeurDeBillet;

import com.openclassrooms.DistributeurDeBillet.Entity.Customer;
import com.openclassrooms.DistributeurDeBillet.Entity.DTO.CustomerDTO;
import com.openclassrooms.DistributeurDeBillet.Entity.DTO.CustomerDepositDTO;
import com.openclassrooms.DistributeurDeBillet.Entity.DTO.CustomerWithdrawDTO;
import com.openclassrooms.DistributeurDeBillet.Entity.Distributeur;
import com.openclassrooms.DistributeurDeBillet.Exception.DepositException;
import com.openclassrooms.DistributeurDeBillet.Repository.CustomerRepository;
import com.openclassrooms.DistributeurDeBillet.Repository.DistributeurRepository;
import com.openclassrooms.DistributeurDeBillet.Service.CustomerService;
import com.openclassrooms.DistributeurDeBillet.Service.CustomerServiceImpl;
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


public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private DistributeurRepository distributeurRepository;
    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private Distributeur distributeur;

    @Before
    public void setUp() throws Exception {
    }

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);

        this.customer = new Customer();
        customer.setAccountBalance(100);
        customer.setFirstName("Alex");
        customer.setName("Marcel");

        this.distributeur = new Distributeur();
        distributeur.setQuantityMoneyAvailable(150);
        distributeur.setId(123L);
        distributeur.setAutomatIdentifier("Automate-123");
    }

    @Test
    public void withdrawNotEnoughMoneyInDistributeur(){
        CustomerWithdrawDTO customerWithdrawDTO = new CustomerWithdrawDTO();
        customerWithdrawDTO.setWithdrawValue(50);
        customerWithdrawDTO.setName("Marcel");
        customerWithdrawDTO.setFirstName("Alex");
        customerWithdrawDTO.setAutomatIdentifier("Automate-123");

        distributeur.setQuantityMoneyAvailable(0);
        Optional<Distributeur> optionalDistributeur = Optional.of(distributeur);
        Optional<Customer> optionalCustomer = Optional.of(customer);
        
        Mockito.when(customerRepository.findOneByNameAndFirstName(any(String.class), any(String.class))).thenReturn(optionalCustomer);
        Mockito.when(distributeurRepository.findByAutomatIdentifier(any(String.class))).thenReturn(optionalDistributeur);

        Assertions.assertThrows(DepositException.class, () -> customerService.withdraw(customerWithdrawDTO));
    }

    @Test
    public void withdrawNotEnoughMoneyOnBankAccount(){
        CustomerWithdrawDTO customerWithdrawDTO = new CustomerWithdrawDTO();
        customerWithdrawDTO.setWithdrawValue(1500);
        customerWithdrawDTO.setName("Marcel");
        customerWithdrawDTO.setFirstName("Alex");
        customerWithdrawDTO.setAutomatIdentifier("Automate-123");

        distributeur.setQuantityMoneyAvailable(1000);
        Optional<Distributeur> optionalDistributeur = Optional.of(distributeur);
        Optional<Customer> optionalCustomer = Optional.of(customer);

        Mockito.when(customerRepository.findOneByNameAndFirstName(any(String.class), any(String.class))).thenReturn(optionalCustomer);
        Mockito.when(distributeurRepository.findByAutomatIdentifier(any(String.class))).thenReturn(optionalDistributeur);

        Assertions.assertThrows(DepositException.class, () -> customerService.withdraw(customerWithdrawDTO));
    }

    @Test
    public void userNotFound(){
        CustomerWithdrawDTO customerWithdrawDTO = new CustomerWithdrawDTO();
        customerWithdrawDTO.setWithdrawValue(155);
        customerWithdrawDTO.setName("Marcel");
        customerWithdrawDTO.setFirstName("Alex");
        customerWithdrawDTO.setAutomatIdentifier("Automate-123");
        Optional<Customer> optionalCustomer = Optional.of(customer);

        Mockito.when(customerRepository.findOneByNameAndFirstName(any(String.class), any(String.class))).thenReturn(optionalCustomer);

        Assertions.assertThrows(DepositException.class, () -> customerService.withdraw(customerWithdrawDTO));
    }

    @Test
    public void withdraw() throws NotFoundException, DepositException {
        CustomerWithdrawDTO customerWithdrawDTO = new CustomerWithdrawDTO();
        customerWithdrawDTO.setWithdrawValue(100);
        customerWithdrawDTO.setName("Marcel");
        customerWithdrawDTO.setFirstName("Alex");
        customerWithdrawDTO.setAutomatIdentifier("Automate-123");

        distributeur.setQuantityMoneyAvailable(1000);
        Optional<Distributeur> optionalDistributeur = Optional.of(distributeur);
        Optional<Customer> optionalCustomer = Optional.of(customer);

        Mockito.when(customerRepository.findOneByNameAndFirstName(any(String.class), any(String.class))).thenReturn(optionalCustomer);
        Mockito.when(distributeurRepository.findByAutomatIdentifier(any(String.class))).thenReturn(optionalDistributeur);
        Mockito.when(distributeurRepository.save(any())).thenReturn(any());

        Customer customer = new Customer();
        customer.setAccountBalance(10);
        customer.setName("Marcel");
        customer.setFirstName("Alex");

        Assertions.assertEquals(customer.getName(), customerService.withdraw(customerWithdrawDTO).getName());
    }

    @Test
    public void depositTest() throws NotFoundException, DepositException {
        CustomerDTO ret = new CustomerDTO();
        ret.setFirstName("Alex");

        CustomerDepositDTO customerDepositDTO = new CustomerDepositDTO();
        customerDepositDTO.setDepositValue(100);
        customerDepositDTO.setFirstName("Alex");
        customerDepositDTO.setName("Jean");

        Mockito.when(customerRepository.findOneByNameAndFirstName(any(), any())).thenReturn(Optional.of(customer));
        Mockito.when(customerRepository.save(any())).thenReturn(customer);

       Assertions.assertEquals(customerService.deposit(customerDepositDTO).getFirstName(), "Alex");
    }
}
