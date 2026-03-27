package br.com.miguel.ordem_management.api.dto.user;

import br.com.miguel.ordem_management.domain.entity.Role;

import java.time.LocalDateTime;

public record UserResponseDTO(Long id,
                              String username,
                              String email,
                              Role role,
                              boolean active,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt) {
}
