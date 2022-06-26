package com.example.ecommerce.domain.ports.api;

import com.example.ecommerce.domain.dto.PaymentInfo;
import com.example.ecommerce.domain.dto.Purchase;
import com.example.ecommerce.domain.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutServicePort {

    PurchaseResponse placeOrder(Purchase purchase);

    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;
}
