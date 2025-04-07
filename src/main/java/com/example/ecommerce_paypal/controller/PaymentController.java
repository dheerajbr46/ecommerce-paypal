package com.example.ecommerce_paypal.controller;

import com.example.ecommerce_paypal.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaypalService paypalService;

    @GetMapping("/create")
    public String createPayment() {
        try {
            String cancelUrl = "http://localhost:8080/api/payments/cancel";
            String successUrl = "http://localhost:8080/api/payments/success";
            Payment payment = paypalService.createPayment(
                    new BigDecimal("10.00"), // Hardcoded for testing
                    "USD",
                    "Test payment for e-commerce",
                    cancelUrl,
                    successUrl
            );
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    logger.info("Redirecting to PayPal approval URL: {}", link.getHref());
                    return link.getHref(); // Return the PayPal approval URL
                }
            }
            return "Error: No approval URL found";
        } catch (PayPalRESTException e) {
            logger.error("Error creating payment: {}", e.getMessage());
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/success")
    public String paymentSuccess(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                logger.info("Payment approved: {}", payment.getId());
                return "Payment successful: " + payment.getId();
            }
            return "Payment not approved";
        } catch (PayPalRESTException e) {
            logger.error("Error executing payment: {}", e.getMessage());
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/cancel")
    public String paymentCancel() {
        logger.info("Payment cancelled by user");
        return "Payment cancelled";
    }
}
