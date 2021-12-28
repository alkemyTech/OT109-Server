package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.ContactPostDTO;
import com.alkemy.ong.dtos.responses.ContactListDTO;
import com.alkemy.ong.entities.Contact;
import com.alkemy.ong.services.ContactService;
import com.alkemy.ong.services.SendGridService;
import com.alkemy.ong.services.UserService;
import com.alkemy.ong.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private SendGridService sendGridService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ContactPostDTO contactPostDto, HttpServletResponse httpResponse) {
        Contact contactCreated;
        Contact contactToCreate = contactPostDto.toContact();
        contactCreated = contactService.create(contactToCreate);

            //Contact Mail Sending
        httpResponse.addHeader("User-Mail-Sent", String.valueOf(sendGridService.contactMessage(contactPostDto.getName(), contactPostDto.getEmail())));
        return new ResponseEntity<>(contactCreated, HttpStatus.CREATED);
    }

    @GetMapping
    public List<ContactListDTO> getAll() {
        //Falta validación como administrador
        return contactService.findAll();
    }
}
