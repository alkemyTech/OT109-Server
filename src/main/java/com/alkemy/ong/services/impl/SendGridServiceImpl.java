package com.alkemy.ong.services.impl;

import com.alkemy.ong.services.SendGridService;
import com.sendgrid.*;
import java.io.File;
import java.io.FileNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

@Service
public class SendGridServiceImpl implements SendGridService {

    private final Email from = new Email("alkemy109.test@gmail.com");

    @Value("${SENDGRID_API_KEY}")
    private SendGrid sendGridApiKey;

    @Override
    public boolean welcomeMessage(String firstName, String lastName, String email) {
        try {
            sendHtmlEmail(email, "Welcome to OT109-API",
                    getHtmlContent("welcomeEmail"));
            return true;
        } catch (IOException ex) {
            Logger.getLogger(SendGridServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean contactMessage(String firstName, String email) {
        try {
            sendEmail(email, "Contact for OT109-API",
                    "Thanks for your contact " + StringUtils.capitalize(firstName) + ". We will be communicating in the next few days.");
            return true;
        } catch (IOException ex) {
            Logger.getLogger(SendGridServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private void sendEmail(String email, String subjectText, String message) throws IOException {
        String subject = subjectText;
        Email to = new Email(email);
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sendGridApiKey.api(request);
    }

    private void sendHtmlEmail(String email, String subjectText, String message) throws IOException {
        String subject = subjectText;
        Email to = new Email(email);
        Content content = new Content("text/html", message);
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sendGridApiKey.api(request);

    }

    private String getHtmlContent(String fileName) throws FileNotFoundException, IOException {
        File resource = new ClassPathResource(fileName + ".html").getFile();
        String response;
        response = "";
        try (Scanner scanner = new Scanner(resource)) {
            while (scanner.hasNext()) {
                response += scanner.nextLine() + "\n";
            }
        }
        return response;
    }

}
