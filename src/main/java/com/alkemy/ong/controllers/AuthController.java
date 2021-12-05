package com.alkemy.ong.controllers;

import com.alkemy.ong.entities.User;
import com.alkemy.ong.pojos.input.RequestLoginDTO;
import com.alkemy.ong.pojos.input.RequestUserDTO;
import com.alkemy.ong.pojos.output.ListUserDTO;
import com.alkemy.ong.pojos.output.ResponseLoginDTO;
import com.alkemy.ong.repositories.UserRepository;
import com.alkemy.ong.services.AuthService;
import com.alkemy.ong.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public ListUserDTO register(@RequestBody RequestUserDTO requestUserDTO){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        User user = userService.findByEmail(requestUserDTO.getEmail());

        modelMapper.map(requestUserDTO, user);

        userService.create(user);
        ListUserDTO userDTO = new ListUserDTO();
        modelMapper.map(user, userDTO);

        return userDTO;

    }


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseLoginDTO login(@RequestBody RequestLoginDTO loginRequestDTO) throws JsonProcessingException {

        return authService.authentication(loginRequestDTO);
    }

}
