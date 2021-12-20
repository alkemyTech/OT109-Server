package com.alkemy.ong.testController;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.lang.reflect.Executable;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.alkemy.ong.controllers.NewsController;
import com.alkemy.ong.dtos.requests.NewPostPutRequestDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
public class NewsControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private INewsService newsService;

        @Autowired
        private JacksonTester<NewPostPutRequestDTO> jsonNewsPostPutDTO;
        @Autowired
        private JacksonTester<NewDTO> jsonNewsDTO;

        @Test
        @WithUserDetails(value = "admin@admin.com")
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
                MockHttpServletResponse response = mockMvc.perform(get("/news/1")
                                .accept(MediaType.APPLICATION_JSON))
                                .andReturn().getResponse();

                // then
                assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
                assertThat(response.getContentAsString()).isEqualTo(jsonNewsDTO.write(newDTO).getJson());
        }

        @Test
        @WithUserDetails(value = "admin@admin.com")
        void canRetrieveByIdWhenDoesNotExists() throws Exception {

                // given

                given(newsService.getById(1L)).willThrow(new NotFoundException(""));

                // when
                MockHttpServletResponse response = mockMvc.perform(get("/news/1")
                                .accept(MediaType.APPLICATION_JSON))
                                .andReturn().getResponse();

                // then
                assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
                assertThat(response.getContentAsString()).isEmpty();
        }

        @Test
        @WithUserDetails(value = "admin@admin.com")
        void canCreateNews() throws Exception {

                // when
                MockHttpServletResponse response = mockMvc.perform(post("/news")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonNewsPostPutDTO
                                                .write(new NewPostPutRequestDTO("name", "content", "image", 1L))
                                                .getJson()))
                                .andReturn().getResponse();

                // then
                assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
                

        }

        @Test
        @WithUserDetails(value = "admin@admin.com")
        void canRetreiveWhenCreateNewsThrowBadRequest() throws Exception {
                // given
                NewPostPutRequestDTO newPostPutRequestDTO = new NewPostPutRequestDTO();
                newPostPutRequestDTO.setContent(null);
                newPostPutRequestDTO.setName("name");
                newPostPutRequestDTO.setImage("image");
                newPostPutRequestDTO.setCategoryId(1L);
                // when
                MockHttpServletResponse response = mockMvc.perform(post("/news")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonNewsPostPutDTO
                                                .write(newPostPutRequestDTO)
                                                .getJson()))
                                .andReturn().getResponse();

                // then
                assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        }

        @Test
        @WithUserDetails(value = "admin@admin.com")
        void canRetreiveWhenCreateNewsThrowNotFound() throws Exception {
                // given
                NewPostPutRequestDTO newPostPutRequestDTO = new NewPostPutRequestDTO();
                newPostPutRequestDTO.setContent("null");
                newPostPutRequestDTO.setName("name");
                newPostPutRequestDTO.setImage("image");
                newPostPutRequestDTO.setCategoryId(1L);
                given(newsService.saveNews(null)).willThrow(new NotFoundException(""));

                // when
                MockHttpServletResponse response = mockMvc.perform(post("/news")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andReturn().getResponse();

                // then
                assertThat(response.getStatus()).isEqualTo(status().isNotFound());
                assertThat(response.getContentAsString()).isEmpty();
        }

        @Test
        @WithUserDetails(value = "admin@admin.com")
        void canUpdateNews() throws Exception {

                // when
                MockHttpServletResponse response = mockMvc.perform(put("/news/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonNewsPostPutDTO
                                                .write(new NewPostPutRequestDTO("name", "content", "image", 1L))
                                                .getJson()))
                                .andReturn().getResponse();

                // then
                assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        }

        @Test
        @WithUserDetails(value = "admin@admin.com")
        void canRetreiveWhenUpdateNewsThrowBadRequest() throws Exception {
                // given
                NewPostPutRequestDTO newPostPutRequestDTO = new NewPostPutRequestDTO();
                newPostPutRequestDTO.setContent(null);
                newPostPutRequestDTO.setName("name");
                newPostPutRequestDTO.setImage("image");
                newPostPutRequestDTO.setCategoryId(1L);
                // when
                MockHttpServletResponse response = mockMvc.perform(post("/news")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonNewsPostPutDTO
                                                .write(newPostPutRequestDTO)
                                                .getJson()))
                                .andReturn().getResponse();

                // then
                assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        }

           public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
