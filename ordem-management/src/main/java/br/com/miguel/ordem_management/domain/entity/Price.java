package br.com.miguel.ordem_management.domain.entity;

import java.time.LocalDateTime;

public class Price {
    private Long id;
    private Product product;
    private Double value;
    private LocalDateTime createdAt;
}
