package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.ActivityPostPutRequestDTO;
import com.alkemy.ong.services.IActivityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ActivityControllerTest {
    //.andDo(print())
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IActivityService iActivityService;

    @InjectMocks
    private ActivityController activityController;

    @Test @Transactional
    @WithUserDetails(value = "admin@admin.com")
    void createWithADMIN_Role() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/activities")
                        .content(asJsonString(new ActivityPostPutRequestDTO("aName","aContent","https://www.image.com/image.png")))
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("aName"))
                .andExpect(jsonPath("$.content").value("aContent"))
                .andExpect(jsonPath("$.image").value("https://www.image.com/image.png"));
    }

    @Test @Transactional
    @WithUserDetails(value = "juanperez@gmail.com")
    void createWithUSER_Role() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/activities")
                        .content(asJsonString(new ActivityPostPutRequestDTO("aName","aContent","https://www.image.com/image.png")))
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("aName"))
                .andExpect(jsonPath("$.content").value("aContent"))
                .andExpect(jsonPath("$.image").value("https://www.image.com/image.png"));
    }

    @Test
    void createWithoutUser() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/activities")
                        .content(asJsonString(new ActivityPostPutRequestDTO("aName","aContent","https://www.image.com")))
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test @Transactional
    @WithUserDetails(value = "admin@admin.com")
    void tryCreateWithValuesNull() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/activities")
                        .content(asJsonString(new ActivityPostPutRequestDTO()))
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test @Transactional
    @WithUserDetails(value = "admin@admin.com")
    void updateWithADMIN_ROle() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .put("/activities/{id}", 1)
                        .content(asJsonString(new ActivityPostPutRequestDTO("aNewName","aNewContent","https://www.newImage.com/image.png")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("aNewName"))
                .andExpect(jsonPath("$.content").value("aNewContent"))
                .andExpect(jsonPath("$.image").value("https://www.newImage.com/image.png"));
    }

    @Test @Transactional
    @WithUserDetails(value = "juanperez@gmail.com")
    void updateWithUSER_Role() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .put("/activities/{id}", 1)
                        .content(asJsonString(new ActivityPostPutRequestDTO("aNewName","aNewContent","https://www.newImage.com/image.png")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("aNewName"))
                .andExpect(jsonPath("$.content").value("aNewContent"))
                .andExpect(jsonPath("$.image").value("https://www.newImage.com/image.png"));
    }

    @Test
    void updateWithoutUser() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .put("/activities/{id}", 1)
                        .content(asJsonString(new ActivityPostPutRequestDTO("aNewName","aNewContent","https://www.newImage.com/image.png")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test @Transactional
    @WithUserDetails(value = "admin@admin.com")
    void tryUpdateWithIdNotExists() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .put("/activities/{id}", 123*5+10)
                        .content(asJsonString(new ActivityPostPutRequestDTO("aNewName","aNewContent","https://www.newImage.com/image.png")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test @Transactional
    @WithUserDetails(value = "admin@admin.com")
    void tryUpdateWithValuesNull() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .put("/activities/{id}", 1)
                        .content(asJsonString(new ActivityPostPutRequestDTO()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
