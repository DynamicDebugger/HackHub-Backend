package com.bs.controller;

import com.bs.model.Chat;
import com.bs.model.Message;
import com.bs.model.User;
import com.bs.request.CreateMessageRequest;
import com.bs.service.ChatService;
import com.bs.service.MessageService;
import com.bs.service.ProjectService;
import com.bs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest request) throws Exception{
        User user = userService.findUserById(request.getSenderId());
        if (user == null) throw new Exception("user not found with id" + request.getSenderId());
        Chat chats = projectService.getProjectById(request.getProjectId()).getChat();
        if (chats == null) throw new Exception("chats not found");

        Message sentMessage = messageService.sendMessage(request.getSenderId(), request.getProjectId(), request.getContent());

        return new ResponseEntity<>(sentMessage, HttpStatus.OK);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessageByChatId(@PathVariable Long projectId) throws Exception{
        List<Message> messages = messageService.getMessageByProjectId(projectId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
