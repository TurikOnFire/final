package com.example.demo.services;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            return;

        userRepository.save(user); // Сохраняем пользователя в базу данных
    }

    public boolean existsByUsernameOrEmail(String username, String email) {
        try {
            User user = userRepository.findByUsernameOrEmail(username, email).orElseThrow();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Transactional
    public User updateUser(Long id, User updatedUser) {

        User user = getUserById(id);

        user.setPassword(updatedUser.getPassword());
        user.setRole(updatedUser.getRole());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }


}