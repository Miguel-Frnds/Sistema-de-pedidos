package br.com.miguel.ordem_management.api.dto.product;

import jakarta.validation.constraints.Size;

public record ProductUpdateRequestDTO(@Size(min = 2, max = 100) String name,
                                      String description) {
}
