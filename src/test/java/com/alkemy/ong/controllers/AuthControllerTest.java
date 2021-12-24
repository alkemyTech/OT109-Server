package com.alkemy.ong.controllers;

import com.alkemy.ong.entities.Role;
import com.alkemy.ong.dtos.requests.createAndUpdate.RegisterUserDTO;
import com.alkemy.ong.dtos.requests.createAndUpdate.RequestLoginDTO;
import com.alkemy.ong.services.RoleService;
import com.alkemy.ong.services.UserService;
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
import org.mockito.Mock;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private AuthController authController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    public void registerTest() throws Exception {
        Role role = new Role();
        role.setName("USER");
        role.setDescription("Usuario con rol User");
        when(roleService.findByName("USER")).thenReturn(role);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register")
                        .content(objectMapper.writeValueAsString(new RegisterUserDTO("Gabriela", "Sosa", "gabriela@gmail.com", "gabrielasosa", "https://www.imagen.png")))
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "tamaraceballos@gmail.com")
    public void registerWithNoNameTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register")
                        .content(objectMapper.writeValueAsString(new RegisterUserDTO(null, "Sosa", "gabriela@gmail.com", "gabrielasosa", "https://imagen.png")))
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "tamaraceballos@gmail.com")
    public void registerWithNoLastNameTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register")
                        .content(objectMapper.writeValueAsString(new RegisterUserDTO("Gabriela", null, "gabriela@gmail.com", "gabrielasosa", "https://imagen.png")))
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "tamaraceballos@gmail.com")
    public void registerWithNoEmailTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register")
                        .content(objectMapper.writeValueAsString(new RegisterUserDTO("Gabriela", "Sosa", null, "gabrielasosa", "https://imagen.png")))
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "tamaraceballos@gmail.com")
    public void registerWithNoPasswordTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register")
                        .content(objectMapper.writeValueAsString(new RegisterUserDTO("Gabriela", "Sosa", "gabriela@gmail.com", null, "https://imagen.png")))
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void loginTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .content(objectMapper.writeValueAsString(new RequestLoginDTO("juanperez@gmail.com", "juanperez")))
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void loginWithIncorrectEmailTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .content(objectMapper.writeValueAsString(new RequestLoginDTO("juanperezz@gmail.com", "juanperez")))
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void loginWithIncorrectPasswordTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .content(objectMapper.writeValueAsString(new RequestLoginDTO("juanperez@gmail.com", "juanperezz")))
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void loginWithNoEmailTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .content(objectMapper.writeValueAsString(new RequestLoginDTO(null, "juanperezz")))
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void loginWithNoPasswordTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .content(objectMapper.writeValueAsString(new RequestLoginDTO("juanperez@gmail.com", null)))
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}
