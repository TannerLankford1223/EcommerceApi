package com.example.ecommerce.application.controller;

import com.example.ecommerce.domain.dto.PaymentInfo;
import com.example.ecommerce.domain.dto.Purchase;
import com.example.ecommerce.domain.dto.PurchaseResponse;
import com.example.ecommerce.domain.ports.api.CheckoutServicePort;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutServicePort checkoutServicePort;

    public CheckoutController(CheckoutServicePort checkoutServicePort) {
        this.checkoutServicePort = checkoutServicePort;
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {
        return checkoutServicePort.placeOrder(purchase);
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException {
        PaymentIntent paymentIntent = checkoutServicePort.createPaymentIntent(paymentInfo);
        String paymentString = paymentIntent.toJson();
        return new ResponseEntity<>(paymentString, HttpStatus.OK);
    }
}
