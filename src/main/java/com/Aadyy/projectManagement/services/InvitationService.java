package com.Aadyy.projectManagement.services;

import com.Aadyy.projectManagement.Modal.Invitation;
import jakarta.mail.MessagingException;

public interface InvitationService {

    public  void  sendInvitation(String email , Long ProjectId) throws MessagingException;
    public Invitation acceptInvitation(String token, Long userId ) throws Exception;

    public  String  getTokenByUserMail(String userEmail);

    void deleteToken(String token);
}
