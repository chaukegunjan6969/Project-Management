package com.Aadyy.projectManagement.services;

import com.Aadyy.projectManagement.Modal.Chat;
import com.Aadyy.projectManagement.Modal.Messages;
import com.Aadyy.projectManagement.Modal.User;
import com.Aadyy.projectManagement.Repository.MessageRepository;
import com.Aadyy.projectManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements  MessageService{


    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  ProjectService projectService;

    //4:41 minute
    @Override
    public Messages sendMessage(Long senderId, Long projectId, String content) throws Exception {

        User sender = userRepository.findById(senderId).orElseThrow(()->new Exception("USer not foudn with id "+ senderId));

        Chat chat = projectService.getChatbyProjectId(projectId);

        Messages messages = new Messages();
        messages.setContent(content);
        messages.setSender(sender);
        messages.setCreatedAt(LocalDateTime.now());
        messages.setChat(chat);

        Messages savedMessage = messageRepository.save(messages);

        chat.getMessages().add(savedMessage);
        return  savedMessage;
    }

    @Override
    public List<Messages> getMessagesByProjectId(Long projectId) throws Exception {

        Chat chat = projectService.getChatbyProjectId(projectId);
        List<Messages> findByChatIdOrderByCreatedAtAsc = messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
        return findByChatIdOrderByCreatedAtAsc;
    }
}
