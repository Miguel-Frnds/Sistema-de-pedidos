package br.com.miguel.ordem_management.api.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemRequestDTO(
        @NotNull Long productId,
        @NotNull @Positive Integer quantity) {
}
