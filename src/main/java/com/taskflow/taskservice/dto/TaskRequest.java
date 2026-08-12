package com.taskflow.taskservice.dto;

import com.taskflow.taskservice.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
    private TaskStatus status;
    private Long userId;
}
