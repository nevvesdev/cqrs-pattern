package br.com.joaodddev.orders.infrastructure.messaging;

import br.com.joaodddev.orders.domain.event.OrderCreatedEvent;
import br.com.joaodddev.orders.domain.model.Order;
import br.com.joaodddev.orders.domain.service.PedidoDomainService;
import org.springframework.stereotype.Service;

@Service
public class PedidoDomainServiceImpl implements PedidoDomainService {

    private final OutboxEventService outboxEventService;

    public PedidoDomainServiceImpl(OutboxEventService outboxEventService) {
        this.outboxEventService = outboxEventService;
    }

    @Override
    public OrderCreatedEvent orderCreatedEvent(Order order) {
        return OrderCreatedEvent.from(order);
    }

    @Override
    public void publishEvent(OrderCreatedEvent event) {
        // grava na outbox (ainda não envia para Kafka diretamente)
        outboxEventService.gravarEvento(event);
    }
}