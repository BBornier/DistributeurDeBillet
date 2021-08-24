package com.openclassrooms.DistributeurDeBillet.Service;

import com.openclassrooms.DistributeurDeBillet.Entity.Customer;
import com.openclassrooms.DistributeurDeBillet.Entity.DTO.CustomerDTO;
import com.openclassrooms.DistributeurDeBillet.Entity.DTO.CustomerDepositDTO;
import com.openclassrooms.DistributeurDeBillet.Entity.DTO.CustomerWithdrawDTO;
import com.openclassrooms.DistributeurDeBillet.Entity.Distributeur;
import com.openclassrooms.DistributeurDeBillet.Exception.DepositException;
import com.openclassrooms.DistributeurDeBillet.Repository.CustomerRepository;
import com.openclassrooms.DistributeurDeBillet.Repository.DistributeurRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final DistributeurRepository distributeurRepository;

    public CustomerDTO deposit(CustomerDepositDTO customerDTO) throws DepositException, NotFoundException {
        validateValue(customerDTO.getDepositValue());
        Customer customer = getValidCustomer(customerDTO.getName(), customerDTO.getFirstName());
        customer.setAccountBalance(customerDTO.getDepositValue() + customer.getAccountBalance());
        customerRepository.save(customer);

        CustomerDTO ret = new CustomerDTO();
        ret.setAccountBalance(customer.getAccountBalance());
        ret.setName(customer.getName());
        ret.setFirstName(customer.getFirstName());
        return ret;
    }


    public CustomerDTO withdraw(CustomerWithdrawDTO customerWithdrawDTO) throws DepositException, NotFoundException {
        validateValue(customerWithdrawDTO.getWithdrawValue());
        Distributeur distributeur = getValidDistributeur(customerWithdrawDTO.getAutomatIdentifier());
        Customer customer = getValidCustomer(customerWithdrawDTO.getName(), customerWithdrawDTO.getFirstName());
        if (customer.getAccountBalance() > customerWithdrawDTO.getWithdrawValue() || distributeur.getQuantityMoneyAvailable() < customerWithdrawDTO.getWithdrawValue()){
            throw new DepositException();
        }
        customer.setAccountBalance(customer.getAccountBalance() - customerWithdrawDTO.getWithdrawValue());
        customerRepository.save(customer);
        distributeur.setQuantityMoneyAvailable(distributeur.getQuantityMoneyAvailable() - customerWithdrawDTO.getWithdrawValue());
        distributeurRepository.save(distributeur);

        CustomerDTO ret = new CustomerDTO();
        ret.setAccountBalance(customer.getAccountBalance());
        ret.setName(customer.getName());
        ret.setFirstName(customer.getFirstName());
        return ret;
    }

    private Distributeur getValidDistributeur(String automateIdentifier) throws NotFoundException {
        Optional<Distributeur> optionalDistributeur = distributeurRepository.findByAutomatIdentifier(automateIdentifier);
        if (optionalDistributeur.isEmpty()){
            throw new NotFoundException("Distributeur not found");
        }
        return optionalDistributeur.get();
    }

    private Customer getValidCustomer(String name, String firstName) throws NotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findOneByNameAndFirstName(name, firstName);
        if (optionalCustomer.isEmpty()){
            throw new NotFoundException("User not found");
        }
        return optionalCustomer.get();
    }

    private void validateValue(Integer value) throws DepositException{
        if (value % 10 != 0){
            throw new DepositException();
        }
    }
}
