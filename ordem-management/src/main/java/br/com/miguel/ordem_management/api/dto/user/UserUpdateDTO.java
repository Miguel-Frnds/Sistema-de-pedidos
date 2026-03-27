package br.com.miguel.ordem_management.api.dto.user;

import br.com.miguel.ordem_management.domain.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(@Size(min = 4, max = 12) String username,
                            @Email String email,
                            @Size(min = 4, max = 10) String password,
                            Role role) {
}
