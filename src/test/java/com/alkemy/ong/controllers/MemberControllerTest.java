package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.responses.ListMemberDTO;
import com.alkemy.ong.entities.OrganizationEntity;
import com.alkemy.ong.services.MemberService;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;


@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

   @Test
   @WithMockUser(username = "dellmdq", password = "fun123")
   void getAll(){
        //given
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Date datenow = sdf.parse(LocalDate.now().toString());
       Date datenow1 = sdf.parse("2020-10-19 21:15:50");
       Date datenow2 = sdf.parse("2021-12-11 09:22:12");
       OrganizationEntity ong1 = new OrganizationEntity(1L, "ONG1");
       ListMemberDTO member0 = new ListMemberDTO(1L,"Pepe","facebook.com","instagram.com","linkedin.com","image.jpg","description", ong1 , datenow2, null, null);
       ListMemberDTO member1 = new ListMemberDTO(2L,"Jose","facebook.com","instagram.com","linkedin.com","image.jpg","description", ong1, datenow1, null, null);
       ListMemberDTO member2 = new ListMemberDTO(3L,"Marcela","facebook.com","instagram.com","linkedin.com","image.jpg","description", ong1, datenow, null, null);
       ListMemberDTO member3 = new ListMemberDTO(4L,"Maria","facebook.com","instagram.com","linkedin.com","image.jpg","description", ong1, datenow, null, null);
       List<ListMemberDTO> memberDTOS = Arrays.asList(member0, member1, member2, member3);

       when(memberService.findAll()).thenReturn(memberDTOS);

       //when
       mockMvc.perform(MockMvcRequestBuilders.get());

   }
}
