package br.com.miguel.ordem_management.api.dto.order;

import br.com.miguel.ordem_management.domain.entity.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        Long userId,
        List<OrderItemResponseDTO> items,
        BigDecimal total,
        OrderStatus status
) {
}
