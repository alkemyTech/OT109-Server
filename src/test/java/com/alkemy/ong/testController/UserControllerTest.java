package com.alkemy.ong.testController;

import com.alkemy.ong.entities.User;
import com.alkemy.ong.exceptions.UserServiceException;
import com.alkemy.ong.pojos.input.UpdateUserDTO;
import com.alkemy.ong.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.nio.charset.Charset;
import java.util.List;
import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.instanceOf;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private ObjectMapper mapper;

    private ObjectWriter writer;

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @BeforeAll
    public void setup() throws Exception {
        mapper = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        writer = mapper.writer().withDefaultPrettyPrinter();
    }

    @Test
    public void findAllWithoutAuthorityShouldReturnForbidden() throws Exception {
        this.mockMvc.perform(get("/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    public void findAllWithRegularAuthorityShouldReturnUserList() throws Exception {
        this.mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", instanceOf(List.class)));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void findAllWithAdminAuthorityShouldReturnUserList() throws Exception {
        this.mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", instanceOf(List.class)));
    }

    @Test
    @Transactional
    public void updateWithoutAuthorityShouldReturnForbidden() throws Exception {
        this.mockMvc.perform(patch("/users/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @WithMockUser(authorities = {"USER"})
    public void updateWithRegularAuthorityShouldReturnForbidden() throws Exception {
        this.mockMvc.perform(patch("/users/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @WithMockUser(authorities = {"ADMIN"})
    public void updateWithAdminAuthorityShouldReturnOk() throws Exception {
        UpdateUserDTO userUpdate = UpdateUserDTO.builder()
                .firstName("updatedFirstName")
                .lastName("updatedLastName")
                .role("USER")
                .build();

        this.mockMvc.perform(patch("/users/1")
                .content(writer.writeValueAsString(userUpdate))
                .contentType("application/json"))
                .andExpect(status().isOk());

        User user = this.userService.findById(1L);
        assertThat(user.getFirstName().equals(userUpdate.getFirstName()));
        assertThat(user.getLastName().equals(userUpdate.getLastName()));
        assertThat(user.getRole().getName().equals(userUpdate.getRole()));
    }

    @Test
    @Transactional
    @WithMockUser(authorities = {"ADMIN"})
    public void updateWithoutValidBodyShouldReturnBadRequest() throws Exception {
        UpdateUserDTO userUpdate;
        this.mockMvc.perform(patch("/users/1"))
                .andExpect(status().isBadRequest());

        userUpdate = UpdateUserDTO.builder().email("email").build();
        this.mockMvc.perform(patch("/users/1")
                .content(writer.writeValueAsString(userUpdate))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());

        userUpdate = UpdateUserDTO.builder().photo("www.google.com").build();
        this.mockMvc.perform(patch("/users/1")
                .content(writer.writeValueAsString(userUpdate))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());

        userUpdate = UpdateUserDTO.builder().photo(".jpg").build();
        this.mockMvc.perform(patch("/users/1")
                .content(writer.writeValueAsString(userUpdate))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());

        userUpdate = UpdateUserDTO.builder().role("ROLE").build();
        this.mockMvc.perform(patch("/users/1")
                .content(writer.writeValueAsString(userUpdate))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void deleteWithoutAuthorityShouldReturnForbidden() throws Exception {
        this.mockMvc.perform(delete("/users/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @WithMockUser(authorities = {"USER"})
    public void deleteWithRegularAuthorityShouldReturnForbidden() throws Exception {
        this.mockMvc.perform(delete("/users/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @WithMockUser(authorities = {"ADMIN"})
    public void DeleteWithAdminAuthorityShouldReturnOk() throws Exception {
        this.mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());

        assertThat(assertThrows(UserServiceException.class, () -> this.userService.findById(1L)).getMessage().equals("User not found"));
    }
}
