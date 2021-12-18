package com.alkemy.ong.testController;

import com.alkemy.ong.controllers.ActivityController;
import com.alkemy.ong.dtos.requests.ActivityPostPutRequestDTO;
import com.alkemy.ong.dtos.responses.ActivityDTO;
import com.alkemy.ong.exceptions.NewsExceptionHandler;
import com.alkemy.ong.services.IActivityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IActivityService iActivityService;

    @InjectMocks
    private ActivityController activityController;

    @BeforeEach
    void setUp() {

        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(activityController)
                .setControllerAdvice(new NewsExceptionHandler())
                .build();

    }

    @Test
    void create() throws Exception {
        System.out.println(mockMvc.perform( MockMvcRequestBuilders
                        .post("/activities")
                        .content(asJsonString(new ActivityPostPutRequestDTO("aName","aContent","https://www.image.com")))
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.name").value("aName")).andReturn());
    }

    @Test
    void update() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .put("/activities/{id}", 2)
                        .content(asJsonString(new ActivityPostPutRequestDTO("aNewName","aNewContent","https://www.newImage.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("aNewName"))
                .andExpect(jsonPath("$.content").value("aNewContent"))
                .andExpect(jsonPath("$.image").value("https://www.newImage.com"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
