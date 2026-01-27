package com.example.demo;

import com.example.demo.configs.Role;
import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@mail.com");
        user.setPassword("password");
        user.setRole(Role.ROLE_USER);
    }

    @Test
    void shouldReturnAllUsers() {
        List<User> users = List.of(user);

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(1, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void shouldReturnUserWhenFoundById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository).findById(1L);
    }

    @Test
    void shouldCreateNewUserInDatabase() {
        when(userRepository.findByUsernameOrEmail(
                user.getUsername(),
                user.getEmail()
        )).thenReturn(Optional.empty());

        userService.createUser(user);

        verify(userRepository).save(user);
    }

    @Test
    void shouldCheckIfUsernameOrEmailExists() {
        when(userRepository.findByUsernameOrEmail(
                user.getUsername(),
                user.getEmail()
        )).thenReturn(Optional.of(user));

        boolean exists = userService.existsByUsernameOrEmail(
                user.getUsername(),
                user.getEmail()
        );

        assertTrue(exists);
    }

    @Test
    void shouldUpdateUserPasswordOrRole() {
        User updatedUser = new User();
        updatedUser.setPassword("newPassword");
        updatedUser.setRole(Role.ROLE_ADMIN);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.updateUser(1L, updatedUser);

        assertEquals("newPassword", result.getPassword());
        assertEquals(Role.ROLE_ADMIN, result.getRole());

        verify(userRepository).save(user);
    }

    @Test
    void shouldDeleteUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).delete(user);
    }


}
