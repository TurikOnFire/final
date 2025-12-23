package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

//    @GetMapping
//    public List<User> getAllUsers() {
//        // Возвращает список всех пользователей
//    }
//
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
//
//    @PostMapping
//    public User createUser(@RequestBody User user) {
//        // Создает нового пользователя
//    }
//
//    @PutMapping("/{id}")
//    public User updateUser(@PathVariable Long id, @RequestBody User user) {
//        // Обновляет данные пользователя
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity
//    deleteUser(@PathVariable Long id) {
//        // Удаляет пользователя
//    }
}
