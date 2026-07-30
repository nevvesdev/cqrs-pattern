package br.com.joaodddev.orders.application.usecase;

import br.com.joaodddev.orders.domain.event.OrderCreatedEvent;
import br.com.joaodddev.orders.domain.model.Order;
import br.com.joaodddev.orders.domain.repository.OrderRepository;
import br.com.joaodddev.orders.domain.service.PedidoDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CriarPedidoUseCase {

    private final OrderRepository orderRepository;
    private final PedidoDomainService domainService;

    public CriarPedidoUseCase(OrderRepository orderRepository,
                              PedidoDomainService domainService) {
        this.orderRepository = orderRepository;
        this.domainService = domainService;
    }

    @Transactional
    public Order execute(String customerId, BigDecimal totalAmount) {
        var order = new Order(UUID.randomUUID().toString(), customerId, totalAmount);
        orderRepository.save(order);

        var evento = domainService.orderCreatedEvent(order);
        domainService.publishEvent(evento);

        return order;
    }
}