package br.com.miguel.ordem_management.api.dto.product;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductUpdateRequestDTO(@Size(min = 2, max = 100) String name,
                                      String description,
                                      @Positive BigDecimal price) {
}
