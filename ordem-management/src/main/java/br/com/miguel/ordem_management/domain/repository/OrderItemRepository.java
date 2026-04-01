package br.com.miguel.ordem_management.domain.repository;

import br.com.miguel.ordem_management.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
