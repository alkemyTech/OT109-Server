package com.alkemy.ong.controllers;

import com.alkemy.ong.entities.User;
import com.alkemy.ong.exceptions.UserServiceException;
import com.alkemy.ong.pojos.input.RequestLoginDTO;
import com.alkemy.ong.pojos.input.RequestUserDTO;
import com.alkemy.ong.pojos.output.ListUserDTO;
import com.alkemy.ong.pojos.output.ResponseLoginDTO;
import com.alkemy.ong.services.UserDetailsServices;
import com.alkemy.ong.services.UserService;
import com.alkemy.ong.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServices userDetailsServices;
    @Autowired
    private JwtUtil jwtTokenUtil;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public ListUserDTO register(@RequestBody RequestUserDTO requestUserDTO){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        User user = new User();
        modelMapper.map(requestUserDTO, user);
        userService.create(user);
        ListUserDTO userDTO = new ListUserDTO();
        modelMapper.map(user, userDTO);
        return userDTO;
    }


    @PostMapping("/login")
    public ResponseLoginDTO login(@RequestBody RequestLoginDTO loginRequestDTO) throws JsonProcessingException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
            );
        }
        catch (BadCredentialsException e) {

            throw new UserServiceException("Bad Credentials");
        }


        final UserDetails userDetails = userDetailsServices
                .loadUserByUsername(loginRequestDTO.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return new ResponseLoginDTO(jwt);
    }

}
