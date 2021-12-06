package com.alkemy.ong.services;

import java.io.IOException;

public interface SendGridService {

    void welcomeMessage(String yourFirstName, String yourLastName, String yourEmail) throws IOException, IOException;
    void contactMessage(String firstName, String email) throws IOException;
}
