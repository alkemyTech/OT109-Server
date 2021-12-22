package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.MemberRequest;
import com.alkemy.ong.dtos.responses.ListMemberDTO;
import com.alkemy.ong.dtos.responses.MemberResponseDTO;
import com.alkemy.ong.entities.Member;
import com.alkemy.ong.entities.OrganizationEntity;
import com.alkemy.ong.exceptions.DataAlreadyExistException;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.pojos.input.RequestLoginDTO;
import com.alkemy.ong.pojos.output.ListOrganizationDTO;
import com.alkemy.ong.services.MemberService;
import com.alkemy.ong.services.OrganizationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MemberService memberService;
    @MockBean
    private OrganizationService organizationService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    void authRequestOnLoginWithAdmin_shouldSucceedWith200() throws Exception {
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

    @Test
    @WithUserDetails(value = "admin@admin.com")
    void findAllWithAdminAuthShouldReturnMemberList() throws Exception {
        //given
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date datenow = sdf.parse("2021-12-17 17:38:02");
        Date datenow1 = sdf.parse("2020-10-19 21:15:50");
        Date datenow2 = sdf.parse("2021-12-11 09:22:12");
        OrganizationEntity ong1 = new OrganizationEntity(1L, "ONG1");
        Member member0 = new Member(1L, "Pepe", "facebook.com", "instagram.com", "linkedin.com", "image.jpg", "description", ong1, datenow2, null, null);
        Member member1 = new Member(2L, "Jose", "facebook.com", "instagram.com", "linkedin.com", "image.jpg", "description", ong1, datenow1, null, null);
        Member member2 = new Member(3L, "Marcela", "facebook.com", "instagram.com", "linkedin.com", "image.jpg", "description", ong1, datenow, null, null);
        Member member3 = new Member(4L, "Maria", "facebook.com", "instagram.com", "linkedin.com", "image.jpg", "description", ong1, datenow, null, null);
        List<Member> members = Arrays.asList(member0, member1, member2, member3);
        Slice<Member> sliceMember = new SliceImpl<>(members);
        
        
        when(memberService.findAll(anyInt(),anyInt())).thenReturn(sliceMember);

        //when
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/members")
                        .contentType(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
                // .andExpect(jsonPath("$", hasSize(4)))
                // .andExpect(jsonPath("$[0].id").value(1L))
                // .andExpect(jsonPath("$[0].name").value("Pepe"))
                // .andExpect(jsonPath("$[0].organization.id").value(1L))
                // .andExpect(jsonPath("$[0].organization.name").value("ONG1"))
                // .andExpect(jsonPath("$[0].deletedAt").value(IsNull.nullValue()))//chequear que todos tengan deletedAtNull;
                // .andExpect(content().json(objectMapper.writeValueAsString(sliceMember)));

        verify(memberService).findAll(anyInt(), anyInt());
    }

    @Test
    @WithUserDetails(value = "juanperez@gmail.com")
    void findAllWithUserAuthShouldReturnForbidden403Status() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/members"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "admin@admin.com")
    void save() throws Exception {
        //given
        MemberRequest member = new MemberRequest("Teto", "facebook.com", "instagram.com", "linkedin.com", "image.jpg", "description", 1L);
        ListOrganizationDTO ong1 = new ListOrganizationDTO(1L, "ONG1");
        MemberResponseDTO memberResponse = new MemberResponseDTO(1L, "Teto", "facebook.com", "instagram.com", "linkedin.com", "image.jpg", "description", ong1);
        when(memberService.create(any())).thenReturn(memberResponse);
        //when
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/members").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
                //then
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Teto"))
                .andExpect(jsonPath("$.organization.id").value(1L))
                .andExpect(jsonPath("$.organization.name").value("ONG1"));

        verify(memberService).create(any());
    }


    @Test
    @WithUserDetails(value = "juanperez@gmail.com")
    void saveWithUserAuthShouldReturn403ForbiddenStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/members"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "admin@admin.com")
    void saveMemberWithNullOrganizationShouldReturnErrorMessage() throws Exception {
        MemberRequest member = new MemberRequest("Teto", "facebook.com", "instagram.com", "linkedin.com", "image.jpg", "description", null);

        mockMvc.perform(MockMvcRequestBuilders.post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("organizationId: must not be null"));
    }

    @Test
    @WithUserDetails(value = "admin@admin.com")
    void saveMemberWithNonExistentOrganizationIdShouldReturnNotFound404Status() throws Exception {
        //given
        MemberRequest member = new MemberRequest("Teto", "facebook.com", "instagram.com", "linkedin.com", "image.jpg", "description", 99L);
        //when
        when(memberService.create(any())).thenThrow(NotFoundException.class);
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(memberService).create(any());
    }

    @Test
    @WithUserDetails(value = "admin@admin.com")
    void saveMemberThatAlreadyExistShouldReturn409ConflictStatus() throws Exception{
        //given
        MemberRequest member = new MemberRequest("Erik", "facebook.com", "instagram.com", "linkedin.com", "image.jpg", "description", 1L);
        //when
        when(memberService.create(any())).thenThrow(DataAlreadyExistException.class);
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());//https://stackoverflow.com/questions/3825990/http-response-code-for-post-when-resource-already-exists

        verify(memberService).create(any());
    }

    @Test
    @WithUserDetails(value = "admin@admin.com")
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/members/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Member successfully deleted"));

        verify(memberService).delete(1L);
    }

    @Test
    @WithUserDetails(value = "juanperez@gmail.com")
    void deleteMemberWithUserAuthShouldReturn403Forbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/members/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "admin@admin.com")
    void deleteNonExistentMemberShouldReturn404NotFound() throws Exception{
        //when
        doThrow(NotFoundException.class).when(memberService).delete(any());
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/members/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = "admin@admin.com")
    void update() throws Exception {
        //given
//        MemberRequest oldMember = new MemberRequest("Tito", "facebook.com", "instagram.com", "linkedin.com", "image.jpg", "description", 1L);
//        mockMvc.perform(MockMvcRequestBuilders
//                .post("/members")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(oldMember)));

        MemberRequest newMember = new MemberRequest("Tito", "facebook.com", "instagram.com", "linkedin.com", "image.jpg", "NEW description", 1L);
        ListOrganizationDTO ong1 = new ListOrganizationDTO(1L, "ONG1");
        MemberResponseDTO newMemberResponse = new MemberResponseDTO(1L, "Tito", "facebook.com", "instagram.com", "linkedin.com", "image.jpg", "NEW description", ong1);
        when(memberService.update(newMember, 1L)).thenReturn(newMemberResponse);

        //when
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/members/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newMember)))
        //then
                .andExpect(status().isOk());

        verify(memberService).update(any(), any());
    }

    @Test
    @WithUserDetails(value = "juanperez@gmail.com")
    void updateMemberWithUserAuthShouldReturn403Forbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/members/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "admin@admin.com")
    void updateNonExistentMemberShouldReturn404NotFound() throws Exception{
        //given
        MemberRequest newMember = new MemberRequest("Tito", "facebook.com", "instagram.com", "linkedin.com", "image.jpg", "NEW description", 1L);
        //when
        doThrow(NotFoundException.class).when(memberService).update(any(),any());
        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/members/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newMember)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = "admin@admin.com")
    void updateMemberWithNullOrganizationShouldReturnErrorMessage() throws Exception {
        //given
        MemberRequest newMember = new MemberRequest("Tito", "facebook.com", "instagram.com", "linkedin.com", "image.jpg", "NEW description", null);
        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/members/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newMember)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("organizationId: must not be null"));
    }
}
