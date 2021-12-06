package com.alkemy.ong.controllers;

import com.alkemy.ong.entities.OrganizationEntity;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.pojos.input.CreateOrganizationDTO;
import com.alkemy.ong.pojos.input.UpdateOrganizationDTO;
import com.alkemy.ong.pojos.output.FindOrganizationDTO;
import com.alkemy.ong.pojos.output.ListOrganizationDTO;
import com.alkemy.ong.services.OrganizationService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/public")
    @ResponseStatus(HttpStatus.CREATED)
    public OrganizationEntity save(@Valid @RequestBody CreateOrganizationDTO input) {
        OrganizationEntity response = new OrganizationEntity();
        BeanUtils.copyProperties(input, response);
        response = orgService.create(response);
        response.setUpdatedAt(null);
        return response;
    }

    @GetMapping("/public")
    @ResponseStatus(HttpStatus.OK)
    public List<ListOrganizationDTO> findAll() {
        List<ListOrganizationDTO> response = new ArrayList();
        List<OrganizationEntity> organizations = orgService.findAll();
        organizations.forEach(e -> {
            ListOrganizationDTO org = new ListOrganizationDTO();
            BeanUtils.copyProperties(e, org);
            response.add(org);
        });
        return response;
    }

    @GetMapping("/public/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FindOrganizationDTO findById(@PathVariable Long id) {
        OrganizationEntity organization = orgService.findById(id);
        FindOrganizationDTO response = new FindOrganizationDTO();
        BeanUtils.copyProperties(organization, response);
        return response;
    }

    @PatchMapping("/public/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id, @Valid @RequestBody UpdateOrganizationDTO organizationDto) {
        //FALTA LA VALIDACIÓN DE ADMINISTRADOR
        OrganizationEntity organization = new OrganizationEntity();
        BeanUtils.copyProperties(organizationDto, organization);
        orgService.update(id, organization);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String globalExceptionHandler(Exception ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        String response = "";
        for (ObjectError error : ex.getAllErrors()) {
            response += error.getDefaultMessage() + "\n";
        }
        return response;
    }

    @ExceptionHandler(ParamNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String paramNotFoundExceptionHandler(ParamNotFound ex) {
        return ex.getMessage();
    }

}
