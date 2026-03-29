package br.com.miguel.ordem_management.api.dto.price;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PriceRequestDTO(
        @NotNull @Positive BigDecimal value
        ) {
}
