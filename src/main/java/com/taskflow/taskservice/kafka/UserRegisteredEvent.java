package com.taskflow.taskservice.kafka;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisteredEvent {
    private String username;
    private String email;
}
