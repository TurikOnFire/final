package com.example.demo.services;

import com.example.demo.entities.Task;
import com.example.demo.entities.User;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }



    @Autowired
    private TaskRepository taskRepository;

    public void testDatabase() {
        User user = userRepository.findByUsername("john_doe");
        System.out.println("User: " + user.getUsername());

        List<Task> tasks = taskRepository.findByUserId(user.getId());
        tasks.forEach(task -> System.out.println("Task: " + task.getTitle()));
    }
}