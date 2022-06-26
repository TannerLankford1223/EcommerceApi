package com.example.ecommerce.infrastructure.adapters;

import com.example.ecommerce.domain.ports.spi.CustomerPersistencePort;
import com.example.ecommerce.infrastructure.entity.Customer;
import com.example.ecommerce.infrastructure.persistence.CustomerRepository;

import java.util.Optional;

public class CustomerRepoAdapter implements CustomerPersistencePort {

    private final CustomerRepository customerRepo;

    public CustomerRepoAdapter(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerRepo.findByEmail(email);
    }

    @Override
    public void save(Customer customer) {
        customerRepo.save(customer);
    }
}
