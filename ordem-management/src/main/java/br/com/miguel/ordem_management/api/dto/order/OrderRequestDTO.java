package br.com.miguel.ordem_management.api.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record OrderRequestDTO(
        @NotNull Long userId,
        @NotNull @Size(min = 1) List<OrderItemRequestDTO> items) {
}
