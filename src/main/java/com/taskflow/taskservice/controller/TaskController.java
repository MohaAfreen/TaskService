package com.taskflow.taskservice.controller;

import com.taskflow.taskservice.dto.TaskRequest;
import com.taskflow.taskservice.dto.TaskResponse;
import com.taskflow.taskservice.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public String createNewTask(@RequestBody TaskRequest request){
        return taskService.createTask(request);
    }

    @GetMapping
    public List<TaskResponse> getTaskDetails(){
        return taskService.getAllTasks();
    }

    @PutMapping("/{id}")
    public String updateTaskDetails(@PathVariable Long id,@RequestBody TaskRequest request){
        return taskService.updateTask(request,id);
    }
}
