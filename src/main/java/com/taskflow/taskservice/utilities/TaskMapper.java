package com.taskflow.taskservice.utilities;

import com.taskflow.taskservice.dto.TaskRequest;
import com.taskflow.taskservice.dto.TaskResponse;
import com.taskflow.taskservice.entity.Task;
import com.taskflow.taskservice.enums.TaskStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
public class TaskMapper {
    public static Task toEntity(TaskRequest dto,Long userId) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setUserId(userId);
        task.setTaskStatus(dto.getStatus());
        return task;
    }

    public static TaskResponse toDTO(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getTaskStatus().name())
                .userId(task.getUserId())
                .build();
    }
}
