package com.example.ecommerce_paypal.config;

import com.paypal.base.rest.APIContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaypalConfig {
    @Bean
    public APIContext apiContext() {
        return new APIContext(
                "AXyiwrhsM1CHKdAefzOxHi6f_g9T4v4isxdH5HKFlZdl3rdgvP9zr4ofx3_DsJd0h89X5ZZDSwhSeD3a",    // Replace with your Client ID
                "ENyyMYEzDCl16FrLuqMOKA5bRrG4mLURtR5d9TXyNKUO8ELr-S19J-90KGjCukC5idOtp88xu_A-i-lj",       // Replace with your Secret
                "sandbox"            // Use "live" for production later
        );
    }
}