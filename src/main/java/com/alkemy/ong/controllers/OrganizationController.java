package com.alkemy.ong.controllers;

import com.alkemy.ong.entities.OrganizationEntity;
import com.alkemy.ong.pojos.input.RequestOrganizationDTO;
import com.alkemy.ong.pojos.output.ListOrganizationDTO;
import com.alkemy.ong.services.OrganizationService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organization")
public class OrganizationController {
    
    @Autowired
    private OrganizationService orgService;
    
    @GetMapping("/public")
    @ResponseStatus(HttpStatus.OK)
    public List<ListOrganizationDTO> findAll(){
        List<ListOrganizationDTO> response = new ArrayList();
        List<OrganizationEntity> organizations = orgService.findAll();
        organizations.forEach(e -> {
           ListOrganizationDTO organization = new ListOrganizationDTO();
           BeanUtils.copyProperties(e, organization);
           response.add(organization);
        });
        return response;
    }
    
    @PostMapping("/public")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody RequestOrganizationDTO organizationDto){
        //FALTA LA VALIDACIÓN DE ADMINISTRADOR
        
    }
    
}
