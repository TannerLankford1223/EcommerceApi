package com.example.ecommerce.domain.dto;

import com.example.ecommerce.infrastructure.entity.Address;
import com.example.ecommerce.infrastructure.entity.Customer;
import com.example.ecommerce.infrastructure.entity.Order;
import com.example.ecommerce.infrastructure.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
