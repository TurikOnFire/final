package com.example.demo.services;

import com.example.demo.entities.Tasks;
import com.example.demo.repositories.TasksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TasksService {

    private final TasksRepository tasksRepository;

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

        if (task.getStatus().equals("PENDING"))
            task.setStatus("IN_PROGRESS");
        else
            task.setStatus("PENDING");

        return tasksRepository.save(task);
    }
}
