package com.alkemy.ong.testController;

import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.assertj.core.api.Assertions.assertThat;

import com.alkemy.ong.controllers.NewsController;
import com.alkemy.ong.dtos.responses.NewDTO;
import com.alkemy.ong.entities.Category;
import com.alkemy.ong.exceptions.NewsExceptionHandler;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.services.INewsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class NewsControllerTest {

        private MockMvc mockMvc;

        @Mock
        private INewsService newsService;

        @InjectMocks
        private NewsController newsController;

        private JacksonTester<NewDTO> jsonNewsDTO;

        @BeforeEach
        void setUp() {
                JacksonTester.initFields(this, new ObjectMapper());
                mockMvc = MockMvcBuilders.standaloneSetup(newsController)
                                .setControllerAdvice(new NewsExceptionHandler())
                                .build();
        }

        @Test
        void canRetrieveByIdWhenExists() throws Exception {

                // given
                NewDTO newDTO = new NewDTO();
                newDTO.setId(1L);
                newDTO.setContent("content");
                newDTO.setName("name");
                newDTO.setImage("image");
                Category category = new Category();
                newDTO.setCategory(category);
                given(newsService.getById(1L))
                                .willReturn(newDTO);

                // when
                MockHttpServletResponse response = mockMvc.perform(
                                get("/news/1")
                                                .accept(MediaType.APPLICATION_JSON))
                                .andReturn().getResponse();

                // then
                assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
                assertThat(response.getContentAsString()).isEqualTo(
                                jsonNewsDTO.write(newDTO).getJson());
        }

        @Test
        void canRetrieveByIdWhenDoesNotExists() throws Exception {

                // given

                given(newsService.getById(1L)).willThrow(new NotFoundException(""));

                // when
                MockHttpServletResponse response = mockMvc.perform(
                                get("/news/1").accept(MediaType.APPLICATION_JSON))
                                .andReturn().getResponse();

                // then
                assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
                assertThat(response.getContentAsString()).isEmpty();
        }

}
