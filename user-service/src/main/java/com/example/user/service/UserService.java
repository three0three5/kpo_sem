package com.example.user.service;

import com.example.user.domain.entity.TaskStatus;
import com.example.user.domain.entity.User;
import com.example.user.domain.repository.UserRepository;
import com.example.user.dto.TaskDto;
import com.example.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

import static com.example.user.utils.TaskPaths.TASK_CREATE_PATH;

@Service
public class UserService {
    private final RestTemplate restTemplate;
    private final String taskBasePath;
    private final UserRepository userRepository;
    @Autowired
    public UserService(@Value("${microservices.task-path}") String taskBasePath, RestTemplate restTemplate,
                       UserRepository userRepository) {
        this.taskBasePath = taskBasePath;
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
    }
    public TaskDto createTask(TaskDto taskDto) {
        return restTemplate.postForObject(taskBasePath + TASK_CREATE_PATH, taskDto, TaskDto.class);
    }

    public UserDto createUser(UserDto userDto) {
        User user = (new User())
                .setId(UUID.randomUUID())
                .setFio(userDto.getFio())
                .setDoneTasksQuantity(userDto.getDoneTasksQuantity())
                .setCurrentTaskDesc(userDto.getCurrentTaskDesc())
                .setCurrentTaskDeadline(userDto.getCurrentTaskDeadline());
        userRepository.save(user);
        return userDto;
    }

    public List<TaskDto> getAllTasks(UUID userId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(taskBasePath + "/getAllTasks")
                .queryParam("userId", userId);
        TaskDto[] tasks = restTemplate.getForObject(builder.toUriString(), TaskDto[].class);
        if (tasks == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(tasks);
    }


    public void removeOverdue(UUID userId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(taskBasePath + "/removeOverdue")
                .queryParam("userId", userId);
        restTemplate.delete(builder.toUriString());
    }

    public String changeTaskStatus(UUID userId, UUID taskId, TaskStatus taskStatus) {
        if (taskStatus == TaskStatus.DONE) {
            User user = userRepository.getReferenceById(userId);
            user.setDoneTasksQuantity(user.getDoneTasksQuantity() + 1);
        }
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(taskBasePath + "/changeStatus")
                .queryParam("taskId", taskId)
                .queryParam("status", taskStatus);
        return restTemplate.postForObject(builder.toUriString(), "", String.class);
    }

    public TaskDto changeTask(UUID userId, UUID taskId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(taskBasePath + "/getTaskById")
                .queryParam("taskId", taskId);
        TaskDto task = restTemplate.getForObject(builder.toUriString(), TaskDto.class);
        if (task == null) {
            return new TaskDto();
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return new TaskDto();
        }
        User user = userOptional.get();
        user.setCurrentTaskDeadline(task.getDeadline()).setCurrentTaskDesc(task.getTaskDesc());
        userRepository.save(user);
        return task;
    }
}
