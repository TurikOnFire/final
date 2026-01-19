package com.example.demo.services;

import com.example.demo.dto.TaskUpdateDto;
import com.example.demo.entities.Tasks;
import com.example.demo.entities.User;
import com.example.demo.repositories.TasksRepository;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TasksService {

    private final TasksRepository tasksRepository;
    private final UserRepository userRepository;


    public List<Tasks> getAllTasks() {
        return tasksRepository.findAll();
    }

    public Tasks getTaskById(Long id) {
        return tasksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @Transactional
    public Tasks updateTask(Long id) {
        Tasks task = getTaskById(id);

        if (task.getStatus().equals("FINISHED"))
            task.setStatus("IN_PROGRESS");
        else
            task.setStatus("FINISHED");

        return tasksRepository.save(task);
    }

    @Transactional
    public void editTask(TaskUpdateDto dto) {
        Tasks task = tasksRepository.findById(dto.getId())
                .orElseThrow();

            task.setTitle(dto.getTitle());
            task.setDescription(dto.getDescription());
            task.setDeadline(dto.getDeadline());
            task.setStatus(dto.getStatus());

    }

    public void createTask() {
        Tasks newTask = new Tasks();

        newTask.setTitle("");
        newTask.setDescription("");
        newTask.setDeadline(new Date());
        newTask.setStatus("IN_PROGRESS");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        newTask.setUser(currentUser);

        tasksRepository.save(newTask);
    }
}
