package br.com.joaodddev.orders.domain.service;

import br.com.joaodddev.orders.domain.event.OrderCreatedEvent;

public interface PedidoDomainService {
    OrderCreatedEvent orderCreatedEvent(br.com.joaodddev.orders.domain.model.Order order);
    void publishEvent(OrderCreatedEvent event);
}