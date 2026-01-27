package com.example.demo;

import com.example.demo.dto.TaskUpdateDto;
import com.example.demo.entities.Tasks;
import com.example.demo.entities.User;
import com.example.demo.repositories.TasksRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.TasksService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TasksServiceTest {

    @Mock
    private TasksRepository tasksRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TasksService tasksService;

    private Tasks task;
    private User user;
    private TaskUpdateDto taskUpdateDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        task = new Tasks();
        task.setId(1L);
        task.setTitle("Test task");
        task.setDescription("Description");
        task.setStatus("IN_PROGRESS");
        task.setDeadline(new Date());
        task.setUser(user);

        taskUpdateDto = new TaskUpdateDto();
        taskUpdateDto.setId(1L);
        taskUpdateDto.setTitle("Updated title");
        taskUpdateDto.setDescription("Updated description");
        taskUpdateDto.setStatus("FINISHED");
        taskUpdateDto.setDeadline(new Date());
        taskUpdateDto.setUserId(1L);
    }

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldReturnAllTasks() {
        when(tasksRepository.findAll()).thenReturn(List.of(task));

        List<Tasks> result = tasksService.getAllTasks();

        assertEquals(1, result.size());
        verify(tasksRepository).findAll();
    }

    @Test
    void shouldReturnTaskById() {
        when(tasksRepository.findById(1L)).thenReturn(Optional.of(task));

        Tasks result = tasksService.getTaskById(1L);

        assertNotNull(result);
        assertEquals("Test task", result.getTitle());
        verify(tasksRepository).findById(1L);
    }

    @Test
    void shouldUpdateTaskStatusByStatusId() {
        when(tasksRepository.findById(1L)).thenReturn(Optional.of(task));
        when(tasksRepository.save(any(Tasks.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Tasks result = tasksService.updateTaskStatus(1L);

        assertEquals("FINISHED", result.getStatus());
        verify(tasksRepository).save(task);
    }

    @Test
    void shouldUpdateTaskFromTaskUpdateDTO() {
        when(tasksRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        tasksService.editTask(taskUpdateDto);

        assertEquals("Updated title", task.getTitle());
        assertEquals("Updated description", task.getDescription());
        assertEquals("FINISHED", task.getStatus());
        assertEquals(user, task.getUser());

        verify(tasksRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    @Test
    void shouldCreateNewTaskInDatabase() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(authentication.getName()).thenReturn("testuser");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(user));

        tasksService.createTask();

        verify(tasksRepository).save(any(Tasks.class));
    }
}
