package br.com.miguel.ordem_management.api.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductCreateRequestDTO(@NotBlank @Size(min = 2, max = 100) String name,
                                      String description,
                                      @NotNull @Positive BigDecimal price,
                                      @NotNull @Positive Integer quantity) {
}
