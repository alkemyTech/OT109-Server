package com.alkemy.ong.services;

import com.alkemy.ong.entities.User;
import java.util.List;
import org.springframework.stereotype.Service;

public interface UserService {
    
    User create(User user);
    
    List<User> findAll();
    
    List<User> findByRole(String role);
    
    List<User> findByRole(Long id);
    
    boolean emailExists(String email);
    
    User findById(Long id);
    
    User findByEmail(String email);
    
    void update(Long id, User user);
    
    void put(Long id, User user);
    
    void delete(Long id);
    
}
