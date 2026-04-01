package br.com.miguel.ordem_management.domain.repository;

import br.com.miguel.ordem_management.domain.entity.Order;
import br.com.miguel.ordem_management.domain.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByUserIdAndStatus(Long userId, OrderStatus status);
}
