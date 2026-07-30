package br.com.joaodddev.orders.domain.repository;

import br.com.joaodddev.orders.domain.model.Order;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(String id);
}