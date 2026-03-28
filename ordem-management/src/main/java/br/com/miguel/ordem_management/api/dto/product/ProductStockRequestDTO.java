package br.com.miguel.ordem_management.api.dto.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductStockRequestDTO(
        @NotNull @Positive(message = "Quantity must be bigger than zero") Integer quantity
)
{
}
