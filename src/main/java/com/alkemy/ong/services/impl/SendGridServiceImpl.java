package com.alkemy.ong.services.impl;

import com.alkemy.ong.services.SendGridService;
import com.sendgrid.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SendGridServiceImpl implements SendGridService {

    @Value("${SENDGRID_API_KEY}")
    private String sendGridApiKey;

    @Override
    public void welcomeMessage(String firstName, String lastName, String email) throws IOException {
        sendEmail(email,"Welcome to OT109-API",
                "Welcome "+StringUtils.capitalize(firstName)+" "+StringUtils.capitalize(lastName)+". Now you can use us API.");
    }

    @Override
    public void contactMessage(String firstName, String email) throws IOException {
        sendEmail(email,"Contact for OT109-API",
                "Thanks for your contact "+StringUtils.capitalize(firstName)+". We will be communicating in the next few days.");
    }

    private void sendEmail(String email,String subjectText, String message) throws IOException {
        Email from = new Email("alkemy109@gmail.com");
        String subject = subjectText;
        Email to = new Email(email);
        Content content = new Content("text/plain",message);
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
        } catch (IOException ex) {
            throw ex;
        }
    }
}
