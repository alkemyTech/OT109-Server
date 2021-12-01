package com.alkemy.ong.controllers;

import com.alkemy.ong.entities.User;
import com.alkemy.ong.exceptions.UserServiceException;
import com.alkemy.ong.pojos.input.RequestUserDTO;
import com.alkemy.ong.pojos.output.ListUserDTO;
import com.alkemy.ong.services.UserService;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ListUserDTO> findAll(){
        //FALTA LA VALIDACIÓN DE ADMINISTRADOR
        List<ListUserDTO> response = new ArrayList();
        List<User> users = userService.findAll();
        users.forEach(e -> {
            response.add(new ListUserDTO(e));
        });
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody RequestUserDTO userDto){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT).setFieldAccessLevel(AccessLevel.PRIVATE);
        User user = new User();
        modelMapper.map(userDto, user);
        userService.update(id, user);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserServiceException.class)
    public String UserServiceExceptionHandler(UserServiceException ex){
        return ex.getMessage();
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public String NullPointerExceptionHandler(NullPointerException ex){
        return ex.getMessage();
    }
}
