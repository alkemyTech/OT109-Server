package com.alkemy.ong.services;

import com.alkemy.ong.entities.User;
import com.alkemy.ong.exceptions.UserServiceException;
import com.alkemy.ong.pojos.input.RequestLoginDTO;
import com.alkemy.ong.pojos.output.ResponseLoginDTO;
import com.alkemy.ong.repositories.UserRepository;
import com.alkemy.ong.util.JwtUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServices userDetailsServices;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    public ResponseLoginDTO authentication(RequestLoginDTO requestLoginDTO) {

        // SecurityContextHolder.getContext().setAuthentication(authentication);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(requestLoginDTO.getUsername(), requestLoginDTO.getPassword());
        authenticationManager.authenticate(authenticationToken);

        Optional<User> userOptional = userRepository.findByEmail(requestLoginDTO.getUsername());
        if (userOptional.isPresent()){
            ResponseLoginDTO response = new ResponseLoginDTO();

            response.setFirstName(userOptional.get().getFirstName());
            response.setToken(jwtTokenUtil.generateToken(response));
            response.setEmail(userOptional.get().getEmail());
            return response;
        }else {
            throw new UserServiceException("Email not found");
        }





    }


}
