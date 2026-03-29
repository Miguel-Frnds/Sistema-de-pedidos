package br.com.miguel.ordem_management.api.dto.price;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceResponseDTO(
        Long id,
        BigDecimal value,
        LocalDateTime createdAt,
        Long productId
) {
}
