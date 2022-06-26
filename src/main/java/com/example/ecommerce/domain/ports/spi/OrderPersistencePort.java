package com.example.ecommerce.domain.ports.spi;

import com.example.ecommerce.infrastructure.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface OrderPersistencePort {

    Page<Order> findOrdersByCustomer(@Param("email") String email, Pageable pageable);
}
