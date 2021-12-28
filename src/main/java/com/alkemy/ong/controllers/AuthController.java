package com.alkemy.ong.controllers;

import com.alkemy.ong.entities.User;
import com.alkemy.ong.exceptions.DataAlreadyExistException;
import com.alkemy.ong.dtos.requests.RegisterUserDTO;
import com.alkemy.ong.dtos.requests.RequestLoginDTO;
import com.alkemy.ong.dtos.responses.ResponseLoginDTO;
import com.alkemy.ong.dtos.responses.ResponseRegisterDTO;
import com.alkemy.ong.services.SendGridService;
import com.alkemy.ong.dtos.responses.UserProfileDTO;
import com.alkemy.ong.repositories.UserRepository;
import com.alkemy.ong.services.RoleService;
import com.alkemy.ong.services.UserService;
import com.alkemy.ong.services.impl.UserDetailsServices;
import com.alkemy.ong.util.JwtUtil;
import com.alkemy.ong.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtTokenUtil;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsServices userDetailsServices;
    @Autowired
    private SendGridService sendGridService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserDTO registerUserDTO, HttpServletResponse httpResponse)
        throws DataAlreadyExistException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        Optional<User> userOptional = userRepository.findByEmail(registerUserDTO.getEmail());
        if (userOptional.isPresent()) {
            throw new DataAlreadyExistException(String.format("Email %s already exists", registerUserDTO.getEmail()));
        }

        User user = new User();
        user.setFirstName(registerUserDTO.getFirstName());
        user.setLastName(registerUserDTO.getLastName());
        user.setEmail(registerUserDTO.getEmail());
        user.setPassword(registerUserDTO.getPassword());
        user.setPhoto(registerUserDTO.getPhoto());
        user.setRole(roleService.findByName("USER"));

        System.out.println(user);

        userService.create(user);

        ResponseRegisterDTO responseRegisterDTO = new ResponseRegisterDTO();
        modelMapper.map(user, responseRegisterDTO);

        // Welcome mail sending
        httpResponse.addHeader("User-Mail-Sent", String.valueOf(sendGridService.welcomeMessage(registerUserDTO.getFirstName(), registerUserDTO.getLastName(), registerUserDTO.getEmail())));
        
        return ResponseEntity.created(null).body(responseRegisterDTO);

    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> login(@Valid @RequestBody RequestLoginDTO loginRequestDTO){
        Optional<User> userOptional = userRepository.findByEmail(loginRequestDTO.getUsername());

        ResponseLoginDTO loginResponse = new ResponseLoginDTO();

        if (userOptional.isPresent()) {
            UserDetails userDetails = userDetailsServices.loadUserByUsername(loginRequestDTO.getUsername());

            if (passwordEncoder.matches(loginRequestDTO.getPassword(), userDetails.getPassword())) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword());

                authenticationManager.authenticate(authentication);

                String jwt = jwtTokenUtil.generateToken(userDetails);

                loginResponse.setFirstName(userOptional.get().getFirstName());
                loginResponse.setToken(jwt);
                loginResponse.setEmail(userOptional.get().getEmail());
                loginResponse.setRole(userOptional.get().getRole().getName());

            } else {
                return ResponseEntity.badRequest().body("Password doesn't match");
            }

            return ResponseEntity.ok().body(loginResponse);

        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUserProfile(HttpServletRequest httpServletRequest) throws NotFoundException {
        if(httpServletRequest.getHeader("Authorization") == null){
            throw new NotFoundException("User not logged");
        }
        String jwt = httpServletRequest.getHeader("Authorization").substring(7);
        User user = userService.findByEmail(jwtTokenUtil.extractUserEmail(jwt));
        ModelMapper modelMapper = new ModelMapper();
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        modelMapper.map(user, userProfileDTO);

        return ResponseEntity.ok().body(userProfileDTO);
    }
}
