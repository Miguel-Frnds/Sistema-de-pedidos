package br.com.miguel.ordem_management.domain.service;

import br.com.miguel.ordem_management.api.dto.user.UserRequestDTO;
import br.com.miguel.ordem_management.api.dto.user.UserResponseDTO;
import br.com.miguel.ordem_management.api.dto.user.UserUpdateDTO;
import br.com.miguel.ordem_management.domain.entity.User;
import br.com.miguel.ordem_management.domain.exception.*;
import br.com.miguel.ordem_management.domain.repository.UserRepository;
import br.com.miguel.ordem_management.api.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserResponseDTO> findAll(){
        return userRepository.findAll().stream()
                .map(UserMapper::toResponseDTO)
                .toList();
    }

    public UserResponseDTO findById(Long id){
        User foundUser = getById(id);
        return UserMapper.toResponseDTO(foundUser);
    }

    public UserResponseDTO save(UserRequestDTO dto){
        if(userRepository.existsByEmail(dto.email())){
            throw new EmailAlreadyExistsException();
        }

        if(userRepository.existsByUsername(dto.username())){
            throw new UsernameAlreadyExistsException();
        }

        User user = UserMapper.toEntity(dto);
        User savedUser = userRepository.save(user);

        return UserMapper.toResponseDTO(savedUser);
    }

    public UserResponseDTO update(Long id, UserUpdateDTO dto){
        User foundUser = getById(id);

        if(userRepository.existsByEmailAndIdNot(dto.email(), id)){
            throw new EmailAlreadyExistsException();
        }

        if(userRepository.existsByUsernameAndIdNot(dto.username(), id)){
            throw new UsernameAlreadyExistsException();
        }

        if(dto.username() != null && !dto.username().isBlank()){
            foundUser.setUsername(dto.username());
        }

        if(dto.email() != null && !dto.email().isBlank()){
            foundUser.setEmail(dto.email());
        }

        if(dto.password() != null && !dto.password().isBlank()){
            foundUser.setPassword(dto.password());
        }

        if(dto.role() != null){
            foundUser.setRole(dto.role());
        }

        User updateUser = userRepository.save(foundUser);

        return UserMapper.toResponseDTO(updateUser);
    }

    public void softDelete(Long id){
        User foundUser = getById(id);
        foundUser.setActive(false);
        userRepository.save(foundUser);
    }

    public void delete(Long id){
        User foundUser = getById(id);
        userRepository.delete(foundUser);
    }

    private User getById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
