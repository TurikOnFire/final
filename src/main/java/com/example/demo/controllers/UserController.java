package com.example.demo.controllers;

import com.example.demo.entities.Tasks;
import com.example.demo.entities.User;
import com.example.demo.services.TasksService;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TasksService tasksService;

    @GetMapping
    public String getUsers(Model model,
                           ModelMap modelMap) {
        List<User> userList = userService.getAllUsers();
        List<Tasks> tasksList = tasksService.getAllTasks();
        model.addAttribute("users", userList);
        model.addAttribute("tasks", tasksList);
        return "users";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id,
                              Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "edituser";
    }


    @PostMapping("/update/{id}")
    public String updateUser(
            @PathVariable Long id,
            @ModelAttribute User user
    ) {
        userService.updateUser(id, user);
        return "redirect:/users/" + id;
    }

    @PostMapping("/updatetask/{id}")
    public String updateTask(
            @PathVariable Long id
    ) {
        tasksService.updateTask(id);
        return "redirect:/users";
    }

//    @DeleteMapping("/{id}")
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public void deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//    }
}
