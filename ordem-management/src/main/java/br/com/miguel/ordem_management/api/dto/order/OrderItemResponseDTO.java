package br.com.miguel.ordem_management.api.dto.order;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
        Long id,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal price
) {
}
