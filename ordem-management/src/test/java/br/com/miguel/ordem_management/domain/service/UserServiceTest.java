package br.com.miguel.ordem_management.domain.service;

import br.com.miguel.ordem_management.api.dto.user.UserRequestDTO;
import br.com.miguel.ordem_management.api.dto.user.UserResponseDTO;
import br.com.miguel.ordem_management.api.dto.user.UserUpdateDTO;
import br.com.miguel.ordem_management.domain.entity.Role;
import br.com.miguel.ordem_management.domain.entity.User;
import br.com.miguel.ordem_management.domain.exception.EmailAlreadyExistsException;
import br.com.miguel.ordem_management.domain.exception.UserNotFoundException;
import br.com.miguel.ordem_management.domain.exception.UsernameAlreadyExistsException;
import br.com.miguel.ordem_management.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should return all users mapped to DTO")
    void findAll() {
        User user1 = createUser(1L, "username1", "user1@email.com", "passwordUser1", Role.USER, true);

        User user2 = createUser(2L, "username2", "user2@email.com", "passwordUser2", Role.ADMIN, false);

        List<User> users = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<UserResponseDTO> result = userService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("username1", result.getFirst().username());
        assertEquals("user1@email.com", result.getFirst().email());
        assertEquals("username2", result.getLast().username());
        assertEquals("user2@email.com", result.getLast().email());
    }

    @Test
    @DisplayName("Should return empty list when database is empty")
    void shouldReturnEmptyList() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<UserResponseDTO> result = userService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return a user mapped to DTO by his Id")
    void findUserById() {
        User user = createDefaultUser();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDTO result = userService.findById(1L);

        assertNotNull(result);
        assertEquals("username", result.username());
        assertEquals("user@email.com", result.email());

        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw a exception when user not found")
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.findById(1L));

        assertEquals("User not found with id: " + 1L, exception.getMessage());

        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Should create user when everything is OK")
    void createUser() {
        UserRequestDTO dto = new UserRequestDTO(
                "username",
                "user@email.com",
                "password123",
                Role.USER
        );

        User user = createDefaultUser();

        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(userRepository.existsByUsername(dto.username())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDTO result = userService.save(dto);

        assertNotNull(result);
        assertEquals("username", result.username());
    }

    @Test
    @DisplayName("Should not create user when email already exists")
    void shouldNotCreateUserWhenEmailAlreadyExists() {
        UserRequestDTO dto = new UserRequestDTO(
                "username",
                "user@email.com",
                "password123",
                Role.USER
        );

        when(userRepository.existsByEmail(dto.email())).thenReturn(true);

        EmailAlreadyExistsException exception = assertThrows(EmailAlreadyExistsException.class, () -> userService.save(dto));

        assertEquals("Email already exists", exception.getMessage());

        verify(userRepository).existsByEmail(dto.email());
        verify(userRepository, never()).existsByUsername(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should not create user when username already exists")
    void shouldNotCreateUserWhenUsernameAlreadyExists() {
        UserRequestDTO dto = new UserRequestDTO(
                "username",
                "user@email.com",
                "password123",
                Role.USER
        );

        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(userRepository.existsByUsername(dto.username())).thenReturn(true);

        UsernameAlreadyExistsException exception = assertThrows(UsernameAlreadyExistsException.class, () -> userService.save(dto));

        assertEquals("Username already exists", exception.getMessage());

        verify(userRepository).existsByEmail(dto.email());
        verify(userRepository).existsByUsername(dto.username());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should update user when everything is OK")
    void shouldUpdateUser() {
        UserUpdateDTO dto = new UserUpdateDTO(
                "test",
                "user@email.com",
                "password123",
                Role.ADMIN
        );

        User user = createDefaultUser();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmailAndIdNot(dto.email(), 1L)).thenReturn(false);
        when(userRepository.existsByUsernameAndIdNot(dto.username(), 1L)).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        UserResponseDTO result = userService.update(1L, dto);

        assertNotNull(result);
        assertEquals(dto.username(), result.username());
        assertEquals(dto.email(), result.email());
        assertEquals(dto.password(), user.getPassword());
        assertEquals(dto.role(), result.role());

        verify(userRepository).findById(1L);
        verify(userRepository).existsByEmailAndIdNot(dto.email(), 1L);
        verify(userRepository).existsByUsernameAndIdNot(dto.username(), 1L);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should not update user when new email already exists")
    void shouldNotUpdateUserWhenNewEmailAlreadyExists() {
        UserUpdateDTO dto = new UserUpdateDTO(
                "username",
                "existingEmail@email.com",
                "password321",
                Role.ADMIN
        );

        User user = createDefaultUser();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmailAndIdNot(dto.email(), 1L)).thenReturn(true);

        EmailAlreadyExistsException exception = assertThrows(EmailAlreadyExistsException.class, () -> userService.update(1L, dto));

        assertEquals("Email already exists", exception.getMessage());

        verify(userRepository).findById(1L);
        verify(userRepository).existsByEmailAndIdNot(dto.email(), 1L);
        verify(userRepository, never()).existsByUsernameAndIdNot(dto.username(), 1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should not update user when new username already exists")
    void shouldNotUpdateWhenNewUsernameAlreadyExists() {
        UserUpdateDTO dto = new UserUpdateDTO(
                "existingUsername",
                "user@email.com",
                "password321",
                Role.USER
        );

        User user = createDefaultUser();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmailAndIdNot(dto.email(), 1L)).thenReturn(false);
        when(userRepository.existsByUsernameAndIdNot(dto.username(), 1L)).thenReturn(true);

        UsernameAlreadyExistsException exception = assertThrows(UsernameAlreadyExistsException.class, () -> userService.update(1L, dto));

        assertEquals("Username already exists", exception.getMessage());

        verify(userRepository).findById(1L);
        verify(userRepository).existsByEmailAndIdNot(dto.email(), 1L);
        verify(userRepository).existsByUsernameAndIdNot(dto.username(), 1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should not update user when user is not found")
    void shouldNotUpdateUserWhenUserIsNotFound() {
        UserUpdateDTO dto = new UserUpdateDTO(
                "username",
                "user@email.com",
                "password321",
                Role.USER
        );

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.update(1L, dto));

        assertEquals("User not found with id: " + 1L, exception.getMessage());

        verify(userRepository).findById(1L);
        verify(userRepository, never()).existsByEmailAndIdNot(dto.email(), 1L);
        verify(userRepository, never()).existsByUsernameAndIdNot(dto.username(), 1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should deactivate a user when everything is OK")
    void deactivateUser() {
        User user = createDefaultUser();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.softDelete(1L);

        assertFalse(user.isActive());

        verify(userRepository).findById(1L);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should delete a User when everything is OK")
    void deleteUser() {
        User user = createDefaultUser();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.delete(1L);

        verify(userRepository).findById(1L);
        verify(userRepository).delete(user);
    }

    @Test
    @DisplayName("Should return a User by his Id when exists in the database")
    void getUserById() {
        User user = createDefaultUser();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.getById(1L);

        assertNotNull(foundUser);
        assertEquals("username", foundUser.getUsername());
        assertEquals("user@email.com", foundUser.getEmail());

        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw a custom exception when user not found")
    void cannotGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.getById(1L));

        assertEquals("User not found with id: " + 1L, exception.getMessage());

        verify(userRepository).findById(1L);
    }

    private User createUser(Long id, String username, String email, String password, Role role, boolean active){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setActive(active);

        return user;
    }

    private User createDefaultUser(){
        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setEmail("user@email.com");
        user.setPassword("password321");
        user.setRole(Role.USER);
        user.setActive(true);

        return user;
    }
}