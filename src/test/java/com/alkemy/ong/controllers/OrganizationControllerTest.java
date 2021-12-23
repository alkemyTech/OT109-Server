package com.alkemy.ong.controllers;

import com.alkemy.ong.controllers.OrganizationController;
import com.alkemy.ong.dtos.requests.SlideRequest;
import com.alkemy.ong.entities.OrganizationEntity;
import com.alkemy.ong.entities.Slide;
import com.alkemy.ong.exceptions.ApiExceptionHandler;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.dtos.requests.CreateOrganizationDTO;
import com.alkemy.ong.dtos.requests.UpdateOrganizationDTO;
import com.alkemy.ong.dtos.responses.ListOrganizationDTO;
import com.alkemy.ong.repositories.OrganizationRepository;
import com.alkemy.ong.services.OrganizationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrganizationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrganizationRepository organizationRepository;

    @MockBean
    private OrganizationService organizationService;

    private JacksonTester<CreateOrganizationDTO> orgJson;

    private CreateOrganizationDTO organizationDTO;
    private OrganizationEntity organization;
    private Slide slide;
    private SlideRequest slideRequest;
    private List<Slide> slides;
    private List<SlideRequest> slideRequests;

    @BeforeEach
    void setUp(){
        JacksonTester.initFields(this,new ObjectMapper());

        organizationDTO = new CreateOrganizationDTO();
        organizationDTO.setName("organization");
        organizationDTO.setImage("http://www.myimage.com/image.jpg");
        organizationDTO.setEmail("organization@org.com");
        organizationDTO.setAddress("address");
        organizationDTO.setAboutUsText("about us text");
        organizationDTO.setPhone(223568792);
        organizationDTO.setWelcomeText("welcome text");
        organizationDTO.setLinkedinUrl("http://www.linkedin.com");
        organizationDTO.setFacebookUrl("http://www.facebook.com");
        organizationDTO.setInstagramUrl("http://www.instagram.com");

        organization = new OrganizationEntity();
        organization.setId(1L);
        organization.setName("organization");
        organization.setImage("http://www.myimage.com/image.jpg");
        organization.setEmail("organization@org.com");
        organization.setAddress("address");
        organization.setAboutUsText("about us text");
        organization.setPhone("223568792");
        organization.setWelcomeText("welcome text");
        organization.setLinkedinUrl("http://www.linkedin.com");
        organization.setFacebookUrl("http://www.facebook.com");
        organization.setInstagramUrl("http://www.instagram.com");
        organization.setCreatedAt(new Date());

        slide = new Slide();
        slide.setId(1L);
        slide.setOrganization(organization);
        slide.setOrderNum(1);
        slide.setText("testslide");
        slide.setImageUrl("https://www.slide.com/image.jpg");
        slides = new ArrayList<>();
        slides.add(slide);
        organization.setSlide(slides);

        slideRequest = new SlideRequest();
        slideRequest.setOrganization_id(slide.getOrganization().getId());
        slideRequest.setText(slide.getText());
        slideRequest.setOrderNum(slide.getOrderNum());
        slideRequest.setImageUrl(slide.getImageUrl());
        slideRequests = new ArrayList<>();
        slideRequests.add(slideRequest);

        ListOrganizationDTO listOrgDTO = new ListOrganizationDTO();
        listOrgDTO.setId(organization.getId());
        listOrgDTO.setName(organization.getName());

    }

    @Test
    @WithUserDetails(value = "monicasala@gmail.com")
    public void adminCanCreateNewOrganization() throws Exception{
        when(organizationService.create(any(OrganizationEntity.class))).thenReturn(organization);

        mvc.perform(post("/organization/public")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orgJson.write(organizationDTO).getJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = "monicasala@gmail.com")
    public void adminTriesPostEndpointWithInvalidInputs()throws Exception{
        organization.setEmail("email");
        mvc.perform(post("/organization/public")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(organization)))
                .andExpect(status().is4xxClientError())
                .andExpect(error -> assertTrue(error.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    @WithUserDetails(value = "juanperez@gmail.com")
    public void userCanRetrieveAllOrganizations() throws Exception{

        when(organizationService.findAll()).thenReturn(Arrays.asList(organization));

        mvc.perform(get("/organization/public").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", instanceOf(List.class)))
                .andExpect(jsonPath("$[0].id").value(1L));

        verify(organizationService,times(1)).findAll();
    }

    @Test
    @WithUserDetails(value = "monicasala@gmail.com")
    public void adminCanRetrieveOrganizationById() throws Exception {

        when(organizationService.findById(1L)).thenReturn(organization);

        mvc.perform(get("/organization/public/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("organization"))
                .andExpect(jsonPath("$.slide", Matchers.hasSize(1)));

        verify(organizationService, times(1)).findById(1L);
    }

    @Test
    @WithUserDetails(value = "monicasala@gmail.com")
    public void adminRetrievesOrganizationWithWrongIdErrorThrown() throws Exception{
        when(organizationService.findById(10L)).thenThrow(new ParamNotFound("Organization not found"));

        mvc.perform(get("/organization/public/{id}",10L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(organizationService, times(1)).findById(10L);
    }

    @Test
    @WithUserDetails(value = "monicasala@gmail.com")
    public void adminCanUpdateOrganization() throws Exception{
        mvc.perform(patch("/organization/public/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new UpdateOrganizationDTO(
                        "updateOrganization",
                        "http://www.myimage.com/updatedImage.jpg",
                        8658264,
                        "updatedAddress",
                        "http://www.linkedin.com",
                        "http://www.facebook.com",
                        "http://www.instagram.com"
                ))))
                .andExpect(status().isOk());
                verify(organizationService, times(1)).update(eq(1L),any(OrganizationEntity.class));
    }

    @Test
    @WithUserDetails(value = "juanperez@gmail.com")
    public void userTriesPatchEnpointErrorThrown() throws Exception{
        mvc.perform(patch("/organization/public/{id}",1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new UpdateOrganizationDTO(
                        "failOrganization",
                        "http://www.myimage.com/failImage.jpg",
                        8658264,
                        "failAddress",
                        "http://www.linkedin.com",
                        "http://www.facebook.com",
                        "http://www.instagram.com"
                )))).andExpect(status().is4xxClientError());
    }

    private String toJson(Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException();
        }
    }

}