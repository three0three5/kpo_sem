package com.example.user.controller;

import com.example.user.domain.entity.TaskStatus;
import com.example.user.dto.TaskDto;
import com.example.user.dto.UserDto;
import com.example.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(@RequestBody TaskDto taskDto)  {
        return userService.createTask(taskDto);
    }

    @GetMapping("/getAllTasks")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDto> getAllTasks(@RequestParam UUID userId) {
        return userService.getAllTasks(userId);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registerNewUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @DeleteMapping("/removeOldTasks")
    @ResponseStatus(HttpStatus.OK)
    public void removeOverdue(@RequestParam UUID userId) {
        userService.removeOverdue(userId);
    }

    @PostMapping("/changeStatus")
    @ResponseStatus(HttpStatus.OK)
    public String changeTaskStatus(@RequestParam UUID userId, @RequestParam UUID taskId,
                                    @RequestParam TaskStatus taskStatus) {
        return userService.changeTaskStatus(userId, taskId, taskStatus);
    }

    @PostMapping("/changeCurrentTask")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto changeTask(@RequestParam UUID userId, @RequestParam UUID taskId) {
        return userService.changeTask(userId, taskId);
    }
}
