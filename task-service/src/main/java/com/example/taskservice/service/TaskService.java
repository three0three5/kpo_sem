package com.example.taskservice.service;

import com.example.taskservice.domain.entity.Task;
import com.example.taskservice.domain.entity.TaskStatus;
import com.example.taskservice.domain.repository.TaskRepository;
import com.example.taskservice.dto.TaskDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskDto create(TaskDto taskDto) {
        Task task = new Task()
                .setId(UUID.randomUUID())
                .setDescription(taskDto.getTaskDesc())
                .setPriority(taskDto.getPriority())
                .setStatus(TaskStatus.TODO)
                .setUser_id(taskDto.getUserId())
                .setDeadline(taskDto.getDeadline());
        taskRepository.save(task);
        return taskDto;
    }

    public List<TaskDto> getMostCriticalTasks(UUID userId) {
        List<Task> entities = taskRepository.findMostCriticalTasksByUserId(userId);
        return entities.stream()
                .map(x -> {
                    TaskDto res = new TaskDto();
                    return res
                            .setTaskDesc(x.getDescription())
                            .setPriority(x.getPriority())
                            .setDeadline(x.getDeadline())
                            .setUserId(x.getUser_id());
                })
                .collect(Collectors.toList());
    }

    public List<TaskDto> getAllTasks(UUID userId) {
        List<Task> entities = taskRepository.getAllTasks(userId);
        return entities.stream()
                .map(x -> {
                    TaskDto res = new TaskDto();
                    return res
                            .setTaskDesc(x.getDescription())
                            .setPriority(x.getPriority())
                            .setDeadline(x.getDeadline())
                            .setUserId(x.getUser_id());
                })
                .collect(Collectors.toList());
    }

    public Integer deleteOverdueTasks(UUID userId) {
        List<Task> entities = taskRepository.getOverdueTasks(userId, LocalDate.now());
        if (entities.isEmpty()) {
            return 0;
        }
        taskRepository.deleteAll(entities);
        return entities.size();
    }

    public TaskDto getTaskById(UUID taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            return null;
        }
        Task entity = taskOptional.get();
        return new TaskDto()
                .setUserId(entity.getUser_id())
                .setTaskDesc(entity.getDescription())
                .setPriority(entity.getPriority())
                .setDeadline(entity.getDeadline());
    }

    public boolean changeStatus(UUID taskId, TaskStatus status) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            return false;
        }
        Task task = taskOptional.get();
        if (task.getStatus() == status) return false;
        task.setStatus(status);
        taskRepository.save(task);
        return true;
    }
}
