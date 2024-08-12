package com.Aadyy.projectManagement.services;

import com.Aadyy.projectManagement.Modal.Invitation;
import com.Aadyy.projectManagement.Repository.InvitationRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationserviceImpl implements  InvitationService {


    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private  ServiceEmail serviceEmail;

    @Override
    public void sendInvitation(String email, Long ProjectId) throws MessagingException {

        String invitationToken = UUID.randomUUID().toString();

        Invitation invitation = new Invitation();
        invitation.setEmail(email);
        invitation.setProjectId(ProjectId);
        invitation.setToken(invitationToken);

        invitationRepository.save(invitation);

        String invitationLink ="http://localhost:8080/accept_invitation?token="+invitationToken;
        serviceEmail.sendEmailWithToken(email,invitationLink);
    }

    @Override
    public Invitation acceptInvitation(String token, Long userId) throws Exception {

        Invitation invitation = invitationRepository.findByToken(token);

        if(invitation==null)
        {
            throw  new Exception("Invalid Invitation");
        }
        return invitation;

    }

    @Override
    public String getTokenByUserMail(String userEmail) {

        Invitation invitation = invitationRepository.findByEmail(userEmail);
        return  invitation.getToken();
    }

    @Override
    public void deleteToken(String token) {

        Invitation invitation = invitationRepository.findByToken(token);

        invitationRepository.delete(invitation);

    }
}
