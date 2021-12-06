package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.ContactPostDTO;
import com.alkemy.ong.dtos.responses.ContactListDTO;
import com.alkemy.ong.entities.Contact;
import com.alkemy.ong.services.ContactService;
import com.alkemy.ong.services.SendGridService;
import com.alkemy.ong.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;
    @Autowired
    private ValidatorUtil validatorUtil;
    @Autowired
    private SendGridService sendGridService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ContactPostDTO contactPostDto){
        Contact contactCreated;
        try {
            if (contactPostDto.getName().isEmpty()) {
                return new ResponseEntity<>("Name cannot be empty.", HttpStatus.BAD_REQUEST);
            }
            if (!validatorUtil.isEmailValid(contactPostDto.getEmail())) {
                return new ResponseEntity<>("Invalid email address.", HttpStatus.BAD_REQUEST);
            }
            if (!validatorUtil.isPhoneValid(contactPostDto.getPhone())) {
                return new ResponseEntity<>("Invalid phone number.", HttpStatus.BAD_REQUEST);
            }
            if(contactPostDto.getMessage().isEmpty()){
                return new ResponseEntity<>("Invalid message.", HttpStatus.BAD_REQUEST);
            }
            Contact contactToCreate = contactPostDto.toContact();
            contactCreated = contactService.createContact(contactToCreate);
            sendGridService.contactMessage(contactCreated.getName(),contactCreated.getEmail());
        }catch(NullPointerException | IOException npe){
            System.out.println("Name, email, phone number and message cannot be empty.");
            return new ResponseEntity<>("Name, email, phone number and message cannot be empty.",HttpStatus.BAD_REQUEST);
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
