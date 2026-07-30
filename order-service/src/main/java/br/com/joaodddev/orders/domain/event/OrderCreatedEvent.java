package br.com.joaodddev.orders.domain.event;

import br.com.joaodddev.orders.domain.model.Order;
import java.time.Instant;

public record OrderCreatedEvent(
        String orderId,
        String customerId,
        BigDecimal totalAmount,
        Instant occurredAt
) {
    public static OrderCreatedEvent from(Order order) {
        return new OrderCreatedEvent(
                order.getId(),
                order.getCustomerId(),
                order.getTotalAmount(),
                Instant.now()
        );
    }
}