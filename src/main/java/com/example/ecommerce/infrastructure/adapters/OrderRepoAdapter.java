package com.example.ecommerce.infrastructure.adapters;

import com.example.ecommerce.domain.ports.spi.OrderPersistencePort;
import com.example.ecommerce.infrastructure.entity.Order;
import com.example.ecommerce.infrastructure.persistence.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class OrderRepoAdapter implements OrderPersistencePort {

    private final OrderRepository orderRepo;

    public OrderRepoAdapter(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public Page<Order> findOrdersByCustomer(String email, Pageable pageable) {
        return orderRepo.findByCustomerEmailOrderByDateCreatedDesc(email, pageable);
    }
}
