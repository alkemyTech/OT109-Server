package com.alkemy.ong.services;

public interface SendGridService {

    boolean welcomeMessage(String yourFirstName, String yourLastName, String yourEmail);
    boolean contactMessage(String firstName, String email);
}
