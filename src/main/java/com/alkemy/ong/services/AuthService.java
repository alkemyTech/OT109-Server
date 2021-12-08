package com.alkemy.ong.services;

import com.alkemy.ong.entities.User;
import com.alkemy.ong.exceptions.UserServiceException;
import com.alkemy.ong.pojos.input.RequestLoginDTO;
import com.alkemy.ong.pojos.output.ResponseLoginDTO;
import com.alkemy.ong.repositories.UserRepository;
import com.alkemy.ong.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(requestLoginDTO.getUsername(), requestLoginDTO.getPassword());

        authenticationManager.authenticate(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsServices.loadUserByUsername(requestLoginDTO.getUsername());
                String jwt = jwtTokenUtil.generateToken(userDetails);

        Optional<User> userOptional = userRepository.findByEmail(requestLoginDTO.getUsername());
        if (userOptional.isPresent()){
            ResponseLoginDTO response = new ResponseLoginDTO();

            response.setFirstName(userOptional.get().getFirstName());
            response.setToken(jwt);
            response.setEmail(userOptional.get().getEmail());
            response.setRole(userOptional.get().getRole().getName());
            return response;
        }else {
            throw new UserServiceException("Email not found");
        }





    }


}
