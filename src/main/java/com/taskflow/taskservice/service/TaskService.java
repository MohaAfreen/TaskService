package com.taskflow.taskservice.service;

import com.taskflow.taskservice.dto.TaskRequest;
import com.taskflow.taskservice.dto.TaskResponse;
import com.taskflow.taskservice.entity.Task;
import com.taskflow.taskservice.repository.TaskRepository;
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

    public String createTask(TaskRequest request){
        Task task= Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .userId(request.getUserId())
                .build();
        taskRepository.save(task);
        return "Task created successfully";
    }

    public String updateTask(TaskRequest request, Long taskid){
        Optional<Task> task= taskRepository.findById(taskid);
        if(!task.isEmpty()){
            Task taskObj= Task.builder()
                    .id(taskid)
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .status(request.getStatus())
                    .userId(request.getUserId())
                    .build();
            taskRepository.save(taskObj);
           return "Task updated successfully";
        }

        return "Task with given id does not exists";
    }

    public List<TaskResponse> getAllTasks(){
        List<Task> taskList= taskRepository.findAll();
        return convertToDtoList(taskList);
    }

   public List<TaskResponse> convertToDtoList(List<Task> taskList){
        List<TaskResponse> taskResponseList=taskList.stream().map( task-> new TaskResponse
                (task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getUserId()))
                .collect(Collectors.toList());
        return taskResponseList;
   }


}
