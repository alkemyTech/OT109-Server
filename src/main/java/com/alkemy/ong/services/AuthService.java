package com.alkemy.ong.services;

import com.alkemy.ong.entities.User;
import com.alkemy.ong.pojos.input.RequestLoginDTO;
import com.alkemy.ong.pojos.output.ResponseLoginDTO;
import com.alkemy.ong.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;



@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServices userDetailsServices;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    public ResponseLoginDTO authentication(RequestLoginDTO requestLoginDTO) {


        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(requestLoginDTO.getUsername(), requestLoginDTO.getPassword());
        authenticationManager.authenticate(authenticationToken);

        // SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsServices
                .loadUserByUsername(requestLoginDTO.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        User user = userService.findByEmail(requestLoginDTO.getUsername());

        ResponseLoginDTO response = new ResponseLoginDTO(
                user.getFirstName(),
                jwt,
                user.getEmail(),
                user.getRole().getName()
        );

        return response;
    }


}
