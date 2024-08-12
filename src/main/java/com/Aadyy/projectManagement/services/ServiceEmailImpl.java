package com.Aadyy.projectManagement.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class ServiceEmailImpl implements  ServiceEmail{

    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void sendEmailWithToken(String useremail, String link) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");

        String subject = "Join project team Invitation";
        String text = "Click the link to join the Project team"+link;

        helper.setSubject(subject);
        helper.setText(text, true);
        helper.setTo(useremail);

        try
        {
            javaMailSender.send(mimeMessage);

        }
        catch (MailSendException e)
        {
            throw new MailSendException("Failed To send Email");

        }

    }
}
