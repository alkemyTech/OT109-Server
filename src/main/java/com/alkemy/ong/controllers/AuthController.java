package com.alkemy.ong.controllers;

import com.alkemy.ong.entities.User;
import com.alkemy.ong.exceptions.DataAlreadyExistException;
import com.alkemy.ong.exceptions.UserServiceException;
import com.alkemy.ong.pojos.input.RegisterUserDTO;
import com.alkemy.ong.pojos.input.RequestLoginDTO;
import com.alkemy.ong.pojos.input.RequestUserDTO;
import com.alkemy.ong.pojos.output.ListUserDTO;
import com.alkemy.ong.pojos.output.ResponseLoginDTO;
import com.alkemy.ong.repositories.RoleRepository;
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

import java.util.Optional;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public ListUserDTO register(@RequestBody RegisterUserDTO registerUserDTO) throws DataAlreadyExistException {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        Optional<User> userOptional = userRepository.findByEmail(registerUserDTO.getEmail());
        if(userOptional.isPresent()){
            throw new DataAlreadyExistException(String.format("Email %s already exists", registerUserDTO.getEmail()));
        }

        User user = new User();
        user.setFirstName(registerUserDTO.getFirstName());
        user.setLastName(registerUserDTO.getLastName());
        user.setEmail(registerUserDTO.getEmail());
        user.setPassword(registerUserDTO.getPassword());
        user.setPhoto(registerUserDTO.getPhoto());
        user.setRole(roleRepository.findById(registerUserDTO.getRole()).get());

        System.out.println(user);

        userService.create(user);
        ListUserDTO userDTO = new ListUserDTO();
        modelMapper.map(user, userDTO);

        return userDTO;

    }


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseLoginDTO login(@RequestBody RequestLoginDTO loginRequestDTO){

        return authService.authentication(loginRequestDTO);
    }

}
