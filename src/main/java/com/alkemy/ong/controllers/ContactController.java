package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.ContactPostDTO;
import com.alkemy.ong.dtos.responses.ContactListDTO;
import com.alkemy.ong.entities.Contact;
import com.alkemy.ong.services.ContactService;
import com.alkemy.ong.services.SendGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private SendGridService sendGridService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ContactPostDTO contactPostDto){
        Contact contactCreated;
        try {
            Contact contactToCreate = contactPostDto.toContact();
            contactCreated = contactService.createContact(contactToCreate);
            sendGridService.contactMessage(contactCreated.getName(),contactCreated.getEmail());
        }catch(NullPointerException npe){
            System.out.println("Name, email, phone number and message cannot be empty.");
            return new ResponseEntity<>("Name, email, phone number and message cannot be empty.",HttpStatus.BAD_REQUEST);
        }
        catch (IOException ioe){
            System.out.println("There was a problem with the email service.");
            return new ResponseEntity<>("There was a problem with the email service.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(contactCreated, HttpStatus.CREATED);
    }

    /**Falta validación como administrador*/
    @GetMapping
    public List<ContactListDTO> getAll(){
        //Falta validación como administrador
        List<Contact> contactList = contactService.findAllContacts();
        List<ContactListDTO> contactDTOList = new ArrayList<>();
        for (Contact c : contactList) {
            contactDTOList.add(new ContactListDTO(c));
        }
        return contactDTOList;
    }
}
