package br.com.miguel.ordem_management.api.controller;

import br.com.miguel.ordem_management.api.dto.user.UserUpdateDTO;
import br.com.miguel.ordem_management.domain.service.UserService;
import br.com.miguel.ordem_management.api.dto.user.UserRequestDTO;
import br.com.miguel.ordem_management.api.dto.user.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(){
        List<UserResponseDTO> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){
        UserResponseDTO user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> save(@Valid @RequestBody UserRequestDTO user){
        UserResponseDTO createUser = userService.save(user);
        return ResponseEntity.status(201).body(createUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO user){
        UserResponseDTO updateUser = userService.update(id, user);
        return ResponseEntity.ok().body(updateUser);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> softDelete(@PathVariable Long id){
        userService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
