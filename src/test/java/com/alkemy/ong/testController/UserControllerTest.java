package com.alkemy.ong.testController;

import com.alkemy.ong.entities.User;
import com.alkemy.ong.pojos.input.RequestLoginDTO;
import com.alkemy.ong.pojos.output.ListUserDTO;
import com.alkemy.ong.pojos.output.ResponseLoginDTO;
import com.alkemy.ong.services.UserService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import net.minidev.json.JSONArray;
import org.json.JSONObject;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.instanceOf;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.util.MultiValueMap;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    
    private String adminJwt;
    
    private String regularJwt;
    
    @BeforeAll
    public void setup() throws Exception{
        ObjectMapper mapper = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        
        RequestLoginDTO user = new RequestLoginDTO();
        user.setUsername("monicasala@gmail.com");
        user.setPassword("monicasala");
        
        String requestJson = writer.writeValueAsString(user);
        String response = this.mockMvc.perform(post("/auth/login")
                .content(requestJson).contentType(APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        adminJwt = "Bearer " + (new JSONObject(response).getString("token"));
        
        user.setUsername("juanperez@gmail.com");
        user.setPassword("juanperez");
        
        requestJson = writer.writeValueAsString(user);
        response = this.mockMvc.perform(post("/auth/login")
                .content(requestJson).contentType(APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        regularJwt = "Bearer " + (new JSONObject(response).getString("token"));
    }
    
    @Test
    public void findAllWithoutAuthorityShouldReturnForbidden() throws Exception{
        this.mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    
    @Test
    public void findAllWithRegularAuthorityShouldReturnUserList() throws Exception{
        this.mockMvc.perform(get("/users").header("Authorization", regularJwt))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", instanceOf(List.class)));
    }
    
    @Test
    @WithUserDetails(value = "admin@admn.com")
    public void findAllWithAdminAuthorityShouldReturnUserList() throws Exception{
        this.mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", instanceOf(List.class)));
    }
    
    @Test
    @Transactional
    public void updateWithoutAuthorityShouldReturnForbidden() throws Exception {
        this.mockMvc.perform(patch("/users/"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    
    
    
}
