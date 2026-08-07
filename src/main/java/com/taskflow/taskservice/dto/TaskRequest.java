package com.taskflow.taskservice.dto;

import com.taskflow.taskservice.enums.TaskStatus;
import lombok.Data;

@Data
public class TaskRequest {
    private String title;
    private String description;
    private TaskStatus status;
    private Long userId;
}
