package com.taskflow.taskservice.service;

import com.taskflow.taskservice.dto.TaskRequest;
import com.taskflow.taskservice.dto.TaskResponse;
import com.taskflow.taskservice.entity.Task;
import com.taskflow.taskservice.enums.TaskStatus;
import com.taskflow.taskservice.exception.TaskNotFoundException;
import com.taskflow.taskservice.repository.TaskRepository;
import com.taskflow.taskservice.utilities.TaskMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponse createTask(TaskRequest request,Long userId){
        request.setStatus(TaskStatus.TODO);
        Task task= TaskMapper.toEntity(request,userId);
        return TaskMapper.toDTO(taskRepository.save(task));
    }

    public TaskResponse updateTask(TaskRequest request, Long taskid){
        Task task= taskRepository.findById(taskid)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        Task updatedTask= TaskMapper.toEntity(request,task.getUserId());
        updatedTask.setId(taskid);
        return TaskMapper.toDTO(taskRepository.save(updatedTask));
    }

    public List<TaskResponse> getTasksById(Long userId){
        return taskRepository.findTaskByUserId(userId)
                .stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());

    }

    public void deleteTaskById(Long taskId){
        taskRepository.deleteById(taskId);
    }
}
