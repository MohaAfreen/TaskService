package com.taskflow.taskservice.controller;

import com.taskflow.taskservice.dto.TaskRequest;
import com.taskflow.taskservice.dto.TaskResponse;
import com.taskflow.taskservice.entity.Task;
import com.taskflow.taskservice.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createNewTask(@Valid @RequestBody TaskRequest request, Authentication authentication){
        Long userId= (Long)authentication.getPrincipal();
        return ResponseEntity.ok(taskService.createTask(request,userId));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTaskDetails(Authentication authentication){
        Long userId= (Long)authentication.getPrincipal();
        return ResponseEntity.ok(taskService.getTasksById(userId));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTaskDetails(@PathVariable Long taskId,@Valid @RequestBody TaskRequest request){
        return ResponseEntity.ok(taskService.updateTask(request,taskId));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTaskById(@PathVariable Long taskId){
        taskService.deleteTaskById(taskId);
        return ResponseEntity.ok("Task deleted successfully");
    }

    @GetMapping("/admin/test")
    public String adminTest() {
        return "Admin endpoint accessed successfully";
    }

    @GetMapping("/test-retry")
    public ResponseEntity<String> testRetry() {
        System.out.println(">>> test-retry endpoint called");
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Task service unavailable");
    }

    @GetMapping("/test-ratelimit")
    public ResponseEntity<String> testRateLimit() {
        return ResponseEntity.ok("Task Service is healthy");
    }
}
