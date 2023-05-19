package com.example.taskservice.controller;

import com.example.taskservice.domain.entity.TaskStatus;
import com.example.taskservice.dto.TaskDto;
import com.example.taskservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(@RequestBody TaskDto taskDto) {
        return taskService.create(taskDto);
    }

    /**
     * method with SQL query that finds
     * min by deadline, max by priority, 0 or 1 in status
     * by specified userId
     * @param userId id of the user to find his tasks
     * @return list of task DTO's
     */
    @GetMapping("/getMostCriticalTasks")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDto> getMostCriticalTask(@RequestParam UUID userId) {
        return taskService.getMostCriticalTasks(userId);
    }

    @GetMapping("/getAllTasks")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDto> getAllTasksByUserId(@RequestParam   UUID userId) {
        return taskService.getAllTasks(userId);
    }

    @GetMapping("/getTaskById")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto getTaskById(@RequestParam UUID taskId) {
        return taskService.getTaskById(taskId);
    }

    @PostMapping("/changeStatus")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus changeStatus(@RequestParam UUID taskId, @RequestParam TaskStatus status) {
        if (taskService.changeStatus(taskId, status)) {
            return HttpStatus.OK;
        }
        return HttpStatus.I_AM_A_TEAPOT;
    }

    @DeleteMapping("/removeOverdue")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteOverdueTasks(@RequestParam UUID userId) {
        Integer deleted = taskService.deleteOverdueTasks(userId);
        if (deleted == 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok("Deleted " + deleted + " tasks");
    }
}
