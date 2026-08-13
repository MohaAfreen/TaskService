package com.taskflow.taskservice.kafka;

import com.taskflow.taskservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumer {
   @Autowired
   private UserService userService;

   @Autowired
   private ObjectMapper objectMapper;

   @KafkaListener(topics = "user-registered", groupId = "task-group")
   public void consume(byte[] message) throws Exception{
      UserRegisteredEvent event =
              objectMapper.readValue(message, UserRegisteredEvent.class);
      userService.createUserIfNotExists(event);
   }

   /* To test Dead Letter Topic
     public void consume(byte[] message) throws Exception{
      System.out.println("Received message - intentionally failing");
      throw new RuntimeException("Testing DLT");
     }
    */
}
