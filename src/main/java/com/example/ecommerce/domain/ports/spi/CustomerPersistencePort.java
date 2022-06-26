package com.example.ecommerce.domain.ports.spi;

import com.example.ecommerce.infrastructure.entity.Customer;

import java.util.Optional;

public interface CustomerPersistencePort {

    Optional<Customer> findByEmail(String email);

    void save(Customer customer);
}
