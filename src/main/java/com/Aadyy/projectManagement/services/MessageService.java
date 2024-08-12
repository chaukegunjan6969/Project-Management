package com.Aadyy.projectManagement.services;

import com.Aadyy.projectManagement.Modal.Messages;

import java.util.List;

public interface MessageService {

    Messages sendMessage(Long senderId, Long chatId, String content)throws  Exception;

    List<Messages> getMessagesByProjectId(Long projectId) throws  Exception;
}
