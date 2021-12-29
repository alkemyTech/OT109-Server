package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.TestimonialDTO;
import com.alkemy.ong.entities.TestimonialEntity;
import com.alkemy.ong.exceptions.BadRequestException;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.services.TestimonialService;
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
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TestimonialsControllerTests {
    
    @MockBean
    private TestimonialService testService;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @Transactional
    @WithUserDetails(value = "admin@admin.com")
    void createSucess() throws Exception {
        TestimonialDTO testBean = new TestimonialDTO("newName","https://www.newImage.com/image.png","newContent");
        
        when(testService.create(testBean)).thenReturn(new TestimonialEntity(1L, "newName", "https://www.newImage.com/image.png", "newContent", null, null, null));
        
        mockMvc.perform( MockMvcRequestBuilders
                .post("/testimonials")
                .content(asJsonString(testBean))
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("newName"))
                .andExpect(jsonPath("$.image").value("https://www.newImage.com/image.png"))
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
        TestimonialDTO testBean = new TestimonialDTO("editName","https://www.editImage.com/image.png","editContent");
        
        when(testService.update(1L, testBean)).thenReturn(new TestimonialEntity(1L, "editName", "https://www.editImage.com/image.png", "editContent", null, null, null));
        
        mockMvc.perform( MockMvcRequestBuilders
                .put("/testimonials/{id}", 1)
                .content(asJsonString(testBean))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("editName"))
                .andExpect(jsonPath("$.image").value("https://www.editImage.com/image.png"))
                .andExpect(jsonPath("$.content").value("editContent"));
    }
    
    @Test
    @Transactional
    @WithUserDetails(value = "admin@admin.com")
    void tryUpdateWithIdNotExists() throws Exception {
        TestimonialDTO testBean = new TestimonialDTO("editName","https://www.editImage.com/image.png","editContent");
        
        when(testService.update(0L, testBean)).thenThrow(NotFoundException.class);
        
        mockMvc.perform( MockMvcRequestBuilders
                .put("/testimonials/{id}", 0)
                .content(asJsonString(testBean))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    @Transactional
    @WithUserDetails(value = "admin@admin.com")
    void tryUpdateWithValuesNull() throws Exception {
        TestimonialDTO testBean = new TestimonialDTO("editName","https://www.editImage.com/image.png","editContent");
        
        when(testService.update(1L, testBean)).thenThrow(BadRequestException.class);
        
        mockMvc.perform( MockMvcRequestBuilders
                .put("/testimonials/{id}", 1)
                .content(asJsonString(testBean))
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
        doThrow(new NotFoundException()).when(testService).delete(2L);
        mockMvc.perform( MockMvcRequestBuilders
                .delete("/testimonials/{id}", 2)
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
