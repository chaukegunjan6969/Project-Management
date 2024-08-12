package com.Aadyy.projectManagement.services;

import com.Aadyy.projectManagement.Modal.Chat;
import com.Aadyy.projectManagement.Repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatserviceImpl implements  ChatService{


    @Autowired
    private ChatRepository chatRepository;


    @Override
    public Chat createchat(Chat chat) {
        return  chatRepository.save(chat);
    }
}
