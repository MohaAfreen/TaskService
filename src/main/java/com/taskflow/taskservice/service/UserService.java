package com.taskflow.taskservice.service;

import com.taskflow.taskservice.dto.TaskRequest;
import com.taskflow.taskservice.entity.Task;
import com.taskflow.taskservice.entity.User;
import com.taskflow.taskservice.kafka.UserRegisteredEvent;
import com.taskflow.taskservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    public void createUserIfNotExists(UserRegisteredEvent event){
        if(userRepository.existsByEmail(event.getEmail()))
            return;
        else{
            User newUser= User.builder()
                    .email(event.getEmail())
                    .username(event.getUsername())
                    .build();
            userRepository.save(newUser);
        }
    }

    public void createDefaultTasks(String email){

    }
}
