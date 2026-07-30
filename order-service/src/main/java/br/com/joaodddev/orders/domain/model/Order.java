package br.com.joaodddev.orders.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

public class Order {

    private final String id;
    private final String customerId;
    private final BigDecimal totalAmount;
    private final Instant createdAt;

    public Order(String id, String customerId, BigDecimal totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.createdAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}