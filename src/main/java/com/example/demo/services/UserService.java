package com.example.demo.services;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        userRepository.save(user); // Сохраняем пользователя в базу данных
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User oldUser = getUserById(id);
        userRepository.delete(oldUser);
        userRepository.save(user);
        return getUserById(id);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }


}