package com.alkemy.ong.controllers;

import com.alkemy.ong.entities.OrganizationEntity;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.pojos.input.UpdateOrganizationDTO;
import com.alkemy.ong.pojos.output.ListOrganizationDTO;
import com.alkemy.ong.services.OrganizationService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @PatchMapping("/public/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id, @Valid @RequestBody UpdateOrganizationDTO organizationDto){
        //FALTA LA VALIDACIÓN DE ADMINISTRADOR
        OrganizationEntity organization = new OrganizationEntity();
        BeanUtils.copyProperties(organizationDto, organization);
        orgService.update(id, organization);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String globalExceptionHandler(Exception ex){
        return ex.getMessage();
    }
    
    @ExceptionHandler(ParamNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String paramNotFoundExceptionHandler(ParamNotFound ex){
        return ex.getMessage();
    }
    
}
