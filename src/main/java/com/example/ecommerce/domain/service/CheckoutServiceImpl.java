package com.example.ecommerce.domain.service;

import com.example.ecommerce.domain.dto.PaymentInfo;
import com.example.ecommerce.domain.dto.Purchase;
import com.example.ecommerce.domain.dto.PurchaseResponse;
import com.example.ecommerce.domain.ports.api.CheckoutServicePort;
import com.example.ecommerce.domain.ports.spi.CustomerPersistencePort;
import com.example.ecommerce.infrastructure.entity.Customer;
import com.example.ecommerce.infrastructure.entity.Order;
import com.example.ecommerce.infrastructure.entity.OrderItem;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CheckoutServiceImpl implements CheckoutServicePort {

    private final CustomerPersistencePort customerRepository;

    public CheckoutServiceImpl(CustomerPersistencePort customerRepository,
                               @Value("${stripe.key.secret}") String secretKey) {

        this.customerRepository = customerRepository;
        Stripe.apiKey = secretKey;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        Order order = purchase.getOrder();

        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::add);

        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        Customer customer = purchase.getCustomer();

        String email = customer.getEmail();

        Optional<Customer> customerOpt = customerRepository.findByEmail(email);

        if (customerOpt.isPresent()) {
            customer = customerOpt.get();
        }

        customer.add(order);
        customerRepository.save(customer);

        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("description", "Demo E-Commerce Purchase");
        params.put("receipt_email", paymentInfo.getReceiptEmail());

        return PaymentIntent.create(params);
    }
}
