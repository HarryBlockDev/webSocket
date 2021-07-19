package com.websocket.server.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/hello")
    public void send(SimpMessageHeaderAccessor sha, @Payload String username) {
        // String message = "Hello from " + sha.getUser().getName();
        String sessionId = sha.getSessionId();
        sha.setLeaveMutable(true);
        simpMessagingTemplate.convertAndSendToUser(sessionId, "/queue/messages", "from server" + sessionId, sha.getMessageHeaders());
    }

    @MessageMapping("/hello1")
    @SendToUser("/queue/messages")
    public String send(String username) {
        return "Hello, " + username;
    }
}