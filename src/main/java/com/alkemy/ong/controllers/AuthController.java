package com.alkemy.ong.controllers;

import com.alkemy.ong.entities.User;
import com.alkemy.ong.exceptions.DataAlreadyExistException;
import com.alkemy.ong.pojos.input.RegisterUserDTO;
import com.alkemy.ong.pojos.input.RequestLoginDTO;
import com.alkemy.ong.pojos.output.ResponseLoginDTO;
import com.alkemy.ong.pojos.output.ResponseRegisterDTO;
import com.alkemy.ong.repositories.UserRepository;
import com.alkemy.ong.services.RoleService;
import com.alkemy.ong.services.UserDetailsServices;
import com.alkemy.ong.services.UserService;
import com.alkemy.ong.util.JwtUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


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


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserDTO registerUserDTO, BindingResult result) throws DataAlreadyExistException {

        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> {
                        return "Error en el campo: " + err.getField() + ": " + err.getDefaultMessage();
                    })
                    .collect(Collectors.toList());
            response.put("Verifique los datos ingresados", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

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
        user.setRole(roleService.findByName("ADMIN"));

        System.out.println(user);

        userService.create(user);

        ResponseRegisterDTO responseRegisterDTO = new ResponseRegisterDTO();
        modelMapper.map(user, responseRegisterDTO);


        return ResponseEntity.ok().body(responseRegisterDTO);

    }


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> login(@Valid @RequestBody RequestLoginDTO loginRequestDTO, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> {
                        return "Error en el campo: " + err.getField() + ": " + err.getDefaultMessage();
                    })
                    .collect(Collectors.toList());
            response.put("Verifique los datos ingresados", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        Optional<User> userOptional = userRepository.findByEmail(loginRequestDTO.getUsername());

        ResponseLoginDTO loginResponse = new ResponseLoginDTO();

        if (userOptional.isPresent()) {
            UserDetails userDetails = userDetailsServices.loadUserByUsername(loginRequestDTO.getUsername());
            if (passwordEncoder.matches(loginRequestDTO.getPassword(), userDetails.getPassword())) {
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());

                authenticationManager.authenticate(authentication);

                SecurityContextHolder.getContext().setAuthentication(authentication);

                String jwt = jwtTokenUtil.generateToken(userDetails);

                loginResponse.setFirstName(userOptional.get().getFirstName());
                loginResponse.setToken(jwt);
                loginResponse.setEmail(userOptional.get().getEmail());
                loginResponse.setRole(userOptional.get().getRole().getName());

            } else {
                return ResponseEntity.badRequest().body("No coninciden las contraseñas");
            }

        return ResponseEntity.ok().body(loginResponse);

        } else {
            return ResponseEntity.badRequest().body("No existe el usuario");
        }
    }

}
