package com.example.taskservice.domain.repository;

import com.example.taskservice.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    @Query(value = Queries.findMostCriticalTasksByUserIdQuery, nativeQuery = true)
    List<Task> findMostCriticalTasksByUserId(@Param("userId") UUID userId);

    @Query(value = Queries.getAllTasksQuery, nativeQuery = true)
    List<Task> getAllTasks(UUID userId);

    @Query(value = Queries.getOverdueTasksQuery, nativeQuery = true)
    List<Task> getOverdueTasks(UUID userId, LocalDate currentDate);
}
