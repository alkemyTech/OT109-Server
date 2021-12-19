package com.alkemy.ong.services;

import com.alkemy.ong.entities.Role;
import com.alkemy.ong.entities.User;
import com.alkemy.ong.exceptions.UserServiceException;
import com.alkemy.ong.repositories.RoleRepository;
import com.alkemy.ong.repositories.UserRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public User create(@NonNull User user) {
        user.setCreatedAt(new Date());
        user.setPassword(encoder.encode(user.getPassword()));
       return userRepo.save(user);
    }

    @Transactional
    public User create(String firstName, String lastName, String email, String password, String photo, Role role) {
        return create(User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .photo(photo)
                .role(role)
                .build());
    }

    @Transactional
    public User create(String firstName, String lastName, String email, String password, String photo, @NonNull Long roleId) {
        Role role = roleRepo.getById(roleId);
        return create(firstName, lastName, email, password, photo, role);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public List<User> findByRole(String role) {
        return userRepo.findByRole_Name(role);
    }

    public List<User> findByRole(Role role) {
        return findByRole(role.getName());
    }

    public List<User> findByRole(Long id) {
        return userRepo.findByRole_Id(id);
    }

    public boolean emailExists(String email) {
        Optional<User> users = userRepo.findByEmail(email);
        return users.isPresent();
    }

    public User findById(@NonNull Long id) throws UserServiceException {
        Optional<User> opt = userRepo.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new UserServiceException("User not found");
    }

    public User findByEmail(@NonNull @Email String email) throws UserServiceException {
        Optional<User> opt = userRepo.findByEmailIgnoreCase(email);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new UserServiceException("User not found");
    }

    @Transactional
    public void update(@NonNull Long id, @NonNull User newUser) throws UserServiceException {
        Optional<User> opt = userRepo.findById(id);
        if (opt.isPresent()) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT).setFieldAccessLevel(AccessLevel.PRIVATE);;
            if(newUser.getPassword() != null){
                newUser.setPassword(encoder.encode(newUser.getPassword()));
            }
            User user = opt.get();
            modelMapper.map(newUser, user);
            user.setUpdatedAt(new Date());
            userRepo.save(user);
        } else {
            throw new UserServiceException("User not found");
        }
    }

    @Transactional
    public void put(@NonNull Long id, @NonNull User newUser) {
        newUser.setId(id);
        Optional<User> opt = userRepo.findById(id);
        if (opt.isPresent()) {
            User user = opt.get();
            newUser.setCreatedAt(user.getCreatedAt());
            BeanUtils.copyProperties(newUser, user);
            user.setUpdatedAt(new Date());
            user.setPassword(encoder.encode(newUser.getPassword()));
            userRepo.save(user);
        } else {
            userRepo.putId(id);
            newUser.setCreatedAt(new Date());
            userRepo.save(newUser);
        }
    }

    @Transactional
    public void delete(@NonNull Long id) throws UserServiceException{
        if(userRepo.findById(id).isPresent()){
            userRepo.deleteById(id);
        } else {
            throw new UserServiceException("User not found");
        }
    }

}
