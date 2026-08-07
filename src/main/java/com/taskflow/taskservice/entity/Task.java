package com.taskflow.taskservice.entity;

import com.taskflow.taskservice.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="tasks")
@Setter
@Getter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    @Column(name="user_id")
    private Long userId;
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;
}
