package com.taskflow.taskservice.service;

import com.taskflow.taskservice.dto.TaskRequest;
import com.taskflow.taskservice.dto.TaskResponse;
import com.taskflow.taskservice.entity.Task;
import com.taskflow.taskservice.enums.TaskStatus;
import com.taskflow.taskservice.exception.TaskNotFoundException;
import com.taskflow.taskservice.repository.TaskRepository;
import com.taskflow.taskservice.utilities.TaskMapper;
import jakarta.transaction.Transactional;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CacheManager cacheManager;

    public TaskService(TaskRepository taskRepository, CacheManager cacheManager) {
        this.taskRepository = taskRepository;
        this.cacheManager= cacheManager;
    }

    @CacheEvict(value="tasks", key="#userId")
    public TaskResponse createTask(TaskRequest request,Long userId){
        request.setStatus(TaskStatus.TODO);
        Task task= TaskMapper.toEntity(request,userId);
        return TaskMapper.toDTO(taskRepository.save(task));
    }

    @Transactional
    public TaskResponse updateTask(TaskRequest request, Long taskid){
        Task task= taskRepository.findById(taskid)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        Task updatedTask= TaskMapper.toEntity(request,task.getUserId());
        updatedTask.setId(taskid);
        Task savedTask = taskRepository.save(updatedTask);
        Long userId= savedTask.getUserId();
        Cache cache = cacheManager.getCache("tasks");
        if (cache != null) {
            cache.evict(userId);
        }
        return TaskMapper.toDTO(savedTask);
    }

    @Cacheable(value="tasks", key="#userId")
    public List<TaskResponse> getTasksById(Long userId){
        System.out.println("Fetching tasks from MySQL...");
        return taskRepository.findTaskByUserId(userId)
                .stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());

    }

    public void deleteTaskById(Long taskId){
        Task task= taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        Long userId = task.getUserId();
        taskRepository.deleteById(taskId);
        Cache cache = cacheManager.getCache("tasks");
        if (cache != null) {
            cache.evict(userId);
        }
    }
}
