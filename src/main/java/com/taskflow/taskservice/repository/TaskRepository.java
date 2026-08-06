package com.taskflow.taskservice.repository;

import com.taskflow.taskservice.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {
    Optional<Task> findTaskById(Long taskId);
}
