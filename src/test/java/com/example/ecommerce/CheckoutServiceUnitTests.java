package com.example.ecommerce;

import com.example.ecommerce.domain.dto.Purchase;
import com.example.ecommerce.domain.dto.PurchaseResponse;
import com.example.ecommerce.domain.ports.api.CheckoutServicePort;
import com.example.ecommerce.domain.ports.spi.CustomerPersistencePort;
import com.example.ecommerce.domain.service.CheckoutServiceImpl;
import com.example.ecommerce.infrastructure.entity.Address;
import com.example.ecommerce.infrastructure.entity.Customer;
import com.example.ecommerce.infrastructure.entity.Order;
import com.example.ecommerce.infrastructure.entity.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CheckoutServiceUnitTests {

    @Mock
    CustomerPersistencePort customerRepo;

    @Autowired
    private CheckoutServicePort checkoutServicePort;

    private Purchase purchase;
    private Customer customer;

    @BeforeEach
    public void init() {
        checkoutServicePort = new CheckoutServiceImpl(customerRepo, "This is a fake secret key for testing");
        customer = new Customer(1L, "John", "Doe",
                "john@email.com", new HashSet<>());
        Address address = new Address();
        Set<OrderItem> orderItems = new HashSet<>();
        Date date = Date.from(Instant.now());
        Order order = new Order(1L, "1A5L254Q", 3, new BigDecimal("15.47"),
                "in stock", date, date, orderItems, customer, address, address);
        purchase = new Purchase(customer, address, address, order, orderItems);
    }

    @Test
    public void placeOrder_ReturnsTrackingOrderNumber() {
        when(customerRepo.findByEmail("john@email.com")).thenReturn(Optional.of(customer));
        PurchaseResponse response = checkoutServicePort.placeOrder(purchase);

        assertEquals(36, response.getOrderTrackingNumber().length());
    }

}
