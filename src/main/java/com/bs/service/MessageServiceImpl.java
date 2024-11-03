package com.bs.service;

import com.bs.model.Chat;
import com.bs.model.Message;
import com.bs.model.User;
import com.bs.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageRepository messagerepository;
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {
        User sender = userService.findUserById(senderId);
        Chat chat = projectService.getChatByProjectId(projectId);
        Message message = new Message();

        message.setContent(content);
        message.setSender(sender);
        message.setCreatedAt(LocalDateTime.now());
        message.setChat(chat);

        Message savedMessage = messagerepository.save(message);

        chat.getMessages().add(savedMessage);
        return savedMessage;
    }

    @Override
    public List<Message> getMessageByProjectId(Long projectId) throws Exception {
        Chat chat = projectService.getChatByProjectId(projectId);
        return messagerepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
    }
}
