package com.example.taskservice.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "task")
public class Task {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "priority")
    private Integer priority = 1;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "user_id")
    private UUID user_id;

    @Enumerated
    @Column(name = "status")
    private TaskStatus status;
}
