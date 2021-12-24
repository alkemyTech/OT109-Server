package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.createAndUpdate.TestimonialDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TestimonialsControllerTests {
    @Autowired
    private MockMvc mockMvc;



    @Test
    @Transactional
    @WithUserDetails(value = "admin@admin.com")
    void createSucess() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/testimonials")
                .content(asJsonString(new TestimonialDTO("newName","https://www.newImage.com/image.png","newContent")))
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("newName"))
                .andExpect(jsonPath("$.image").value("https://www.newImage.com"))
                .andExpect(jsonPath("$.content").value("newContent"));
    }
    @Test
    @Transactional
    @WithUserDetails(value = "admin@admin.com")
    public void tryCreateWithValuesNull() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/testimonials")
                .content(asJsonString(new TestimonialDTO()))
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "admin@admin.com")
    void updateSucess() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .put("/testimonials/{id}", 1)
                .content(asJsonString(new TestimonialDTO("editName","https://www.editImage.com/image.png","editContent")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("editName"))
                .andExpect(jsonPath("$.image").value("https://www.editImage.com"))
                .andExpect(jsonPath("$.content").value("editContent"));
    }
    @Test
    @Transactional
    @WithUserDetails(value = "admin@admin.com")
    void tryUpdateWithIdNotExists() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .put("/testimonials/{id}", 123*5+10)
                .content(asJsonString(new TestimonialDTO("editName","https://www.editImage.com/image.png","editContent")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    @Transactional
    @WithUserDetails(value = "admin@admin.com")
    void tryUpdateWithValuesNull() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .put("/testimonials/{id}", 1)
                .content(asJsonString(new TestimonialDTO()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "admin@admin.com")
    void deleteSucess() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .delete("/testimonials/{id}", 3)
                .content(asJsonString(new TestimonialDTO("nameDeleted","https://www.Image.com/image.png","Content")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @Test
    @Transactional
    @WithUserDetails(value = "admin@admin.com")
    void tryDeleteWithIdNotExists() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .delete("/testimonials/{id}", 123*5+10)
                .content(asJsonString(new TestimonialDTO("Name","https://www.Image.com/image.png","Content")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }



    @Test
    void createWithoutUser() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/testimonials")
                .content(asJsonString(new TestimonialDTO("newName","https://www.newImage.com/image.png","newContent")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void updateWithoutUser() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .put("/testimonials/{id}", 1)
                .content(asJsonString(new TestimonialDTO("editName","https://www.editImage.com/image.png","editContent")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void DeleteWithoutUser() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .delete("/testimonials/{id}", 1)
                .content(asJsonString(new TestimonialDTO("Name","https://www.Image.com/image.png","Content")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
