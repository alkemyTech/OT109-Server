package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.ContactPostDTO;
import com.alkemy.ong.dtos.responses.ContactListDTO;
import com.alkemy.ong.entities.Contact;
import com.alkemy.ong.services.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @Autowired
    private ObjectMapper objectMapper;

    Contact contact1 = new Contact(1L, "Marcos", "123456", "marcossanchez@gmail.com", "mensaje", Date.valueOf(LocalDate.now()), null, null);
    Contact contact2 = new Contact(2L, "Pedro", "123456", "pedrorodriguez@gmail.com", "mensaje", Date.valueOf(LocalDate.now()), null, null);
    Contact contact3 = new Contact(3L, "José", "123456", "joseceballos@gmail.com", "mensaje", Date.valueOf(LocalDate.now()), null, null);

    List<ContactListDTO> contactsDto = new ArrayList<>();


    @Test
    @WithUserDetails(value = "marcossanchez@gmail.com")
    public void findAllContactTest() throws Exception {

        ContactListDTO contactDto1 = new ContactListDTO(contact1);
        ContactListDTO contactDto2 = new ContactListDTO(contact2);
        ContactListDTO contactDto3 = new ContactListDTO(contact3);

        contactsDto.add(contactDto1);
        contactsDto.add(contactDto2);
        contactsDto.add(contactDto3);

        when(contactService.findAll()).thenReturn(contactsDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/contacts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Marcos"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Pedro"))
                .andExpect(jsonPath("$[2].id").value(3L))
                .andExpect(jsonPath("$[2].name").value("José"))
                .andExpect(content().json(objectMapper.writeValueAsString(contactsDto)));

        verify(contactService).findAll();
    }

    @Test
    @Transactional
    @WithUserDetails(value = "tamaraceballos@gmail.com")
    public void createContactTest() throws Exception {
        mockMvc.perform(post("/contacts")
                        .content(objectMapper.writeValueAsString(new ContactPostDTO("Gabriela", "35481234567890", "gabriela@gmail.com", "message")))
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "tamaraceballos@gmail.com")
    public void createContactWithNoNameTest() throws Exception {
        mockMvc.perform(
                        post("/contacts")
                                .content(objectMapper.writeValueAsString(new ContactPostDTO(null, "35481234567890", "gabriela@gmail.com", "message")))
                                .characterEncoding("utf-8")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "tamaraceballos@gmail.com")
    public void createContactWithNoEmailTest() throws Exception {
        mockMvc.perform(
                        post("/contacts")
                                .content(objectMapper.writeValueAsString(new ContactPostDTO("Gabriela", "35481234567890", null, "message")))
                                .characterEncoding("utf-8")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "tamaraceballos@gmail.com")
    public void createContactWithNoMessageTest() throws Exception {
        mockMvc.perform(
                        post("/contacts")
                                .content(objectMapper.writeValueAsString(new ContactPostDTO("Gabriela", "35481234567890", "gabriela@gmail.com", null)))
                                .characterEncoding("utf-8")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}
