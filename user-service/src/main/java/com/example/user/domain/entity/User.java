package com.example.user.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "fio")
    private String fio;
    @Column(name = "done_tasks_quantity")
    private Integer doneTasksQuantity;
    @Column(name = "current_task_desc")
    private String currentTaskDesc;
    @Column(name = "current_task_deadline")
    private LocalDate currentTaskDeadline;
}
