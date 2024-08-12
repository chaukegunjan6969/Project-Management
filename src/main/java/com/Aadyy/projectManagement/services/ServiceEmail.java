package com.Aadyy.projectManagement.services;

import jakarta.mail.MessagingException;

public interface ServiceEmail {

    void  sendEmailWithToken(String useremail, String Link) throws MessagingException;
}
