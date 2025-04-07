package com.example.ecommerce_paypal;

import com.example.ecommerce_paypal.domain.Product;
import com.example.ecommerce_paypal.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class EcommercePaypalApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(EcommercePaypalApplication.class);

    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(EcommercePaypalApplication.class, args);
    }

    @Override
    public void run(String... args) {
        logger.info("Seeding sample data...");
        productRepository.save(new Product(null, "Laptop", new BigDecimal("999.99"), "High-end laptop"));
        productRepository.save(new Product(null, "Phone", new BigDecimal("499.99"), "Latest smartphone"));
        productRepository.save(new Product(null, "Headphones", new BigDecimal("99.99"), "Noise-canceling"));
        logger.info("Sample data seeded. Total products: {}", productRepository.count());
    }
}
