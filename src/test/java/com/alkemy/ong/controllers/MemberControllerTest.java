package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.responses.ListMemberDTO;
import com.alkemy.ong.entities.OrganizationEntity;
import com.alkemy.ong.pojos.input.RequestLoginDTO;
import com.alkemy.ong.services.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//@WebMvcTest(MemberController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MemberService memberService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    //@WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
    @Test
    public void authRequestOnLoginWithAdmin_shouldSucceedWith200() throws Exception{
        //Given
        RequestLoginDTO requestLoginDTO = new RequestLoginDTO();
        requestLoginDTO.setUsername("admin@admin.com");
        requestLoginDTO.setPassword("admin");
        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestLoginDTO)))
        //then
                .andExpect(status().isOk());
    }

   //@WithMockUser(username = "admin@admin.com", password = "admin", roles = )
   @Test
   @WithUserDetails(value = "admin@admin.com")
   public void findAll() throws Exception{
        //given
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Date datenow = sdf.parse("2021-12-17 17:38:02");
       Date datenow1 = sdf.parse("2020-10-19 21:15:50");
       Date datenow2 = sdf.parse("2021-12-11 09:22:12");
       OrganizationEntity ong1 = new OrganizationEntity(1L, "ONG1");
       ListMemberDTO member0 = new ListMemberDTO(1L,"Pepe","facebook.com","instagram.com","linkedin.com","image.jpg","description", ong1 , datenow2, null, null);
       ListMemberDTO member1 = new ListMemberDTO(2L,"Jose","facebook.com","instagram.com","linkedin.com","image.jpg","description", ong1, datenow1, null, null);
       ListMemberDTO member2 = new ListMemberDTO(3L,"Marcela","facebook.com","instagram.com","linkedin.com","image.jpg","description", ong1, datenow, null, null);
       ListMemberDTO member3 = new ListMemberDTO(4L,"Maria","facebook.com","instagram.com","linkedin.com","image.jpg","description", ong1, datenow, null, null);
       List<ListMemberDTO> memberDTOS = Arrays.asList(member0, member1, member2, member3);
       System.out.println(memberDTOS);
       when(memberService.findAll()).thenReturn(memberDTOS);

       //when
       mockMvc.perform(MockMvcRequestBuilders
                .get("/members")
                .contentType(MediaType.APPLICATION_JSON))
       //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Pepe"))
                .andExpect(jsonPath("$[0].organization.id").value(1L))
                .andExpect(content().json(objectMapper.writeValueAsString(memberDTOS)));

       verify(memberService).findAll();
   }
}
