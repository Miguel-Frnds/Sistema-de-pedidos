package br.com.miguel.ordem_management.api.dto.user;

import br.com.miguel.ordem_management.domain.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(@NotBlank @Size(min = 4, max = 12) String username,
                             @NotBlank @Email String email,
                             @NotBlank @Size(min = 4, max = 10) String password,
                             @NotNull Role role) {
}
