package br.com.miguel.ordem_management.api.mapper;

import br.com.miguel.ordem_management.domain.entity.User;
import br.com.miguel.ordem_management.api.dto.user.UserRequestDTO;
import br.com.miguel.ordem_management.api.dto.user.UserResponseDTO;

public class UserMapper {
    public static User toEntity(UserRequestDTO dto){
        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setRole(dto.role());
        user.setActive(true);
        return user;
    }

    public static UserResponseDTO toResponseDTO(User user){
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
