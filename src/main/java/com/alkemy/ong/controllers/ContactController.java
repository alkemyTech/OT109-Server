package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.ContactPostDto;
import com.alkemy.ong.entities.Contact;
import com.alkemy.ong.services.ContactService;
import com.alkemy.ong.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;
    @Autowired
    private ValidatorUtil validatorUtil;


    @PostMapping
    public ResponseEntity<?> create(@RequestBody ContactPostDto contactPostDto){
        if(contactPostDto.getName().isEmpty()) {
            return new ResponseEntity<>("Name cannot be empty.",HttpStatus.BAD_REQUEST);
        }
        if(!validatorUtil.isEmailValid(contactPostDto.getEmail())){
            return new ResponseEntity<>("Invalid email address.",HttpStatus.BAD_REQUEST);
        }
        Contact contactToCreate = new Contact();
        contactToCreate.setName(contactPostDto.getName());
        contactToCreate.setPhone(contactPostDto.getPhone());
        contactToCreate.setEmail(contactPostDto.getEmail());
        contactToCreate.setMessage(contactPostDto.getMessage());

        Contact contactCreated = contactService.createContact(contactToCreate);

        return new ResponseEntity<>(contactCreated, HttpStatus.CREATED);
    }
}
