package com.alkemy.ong.services;

import com.alkemy.ong.entities.Role;
import java.util.List;

public interface RoleService {
    
    public Role create(Role role);
    
    public List<Role> findAll();
    
    public Role findById(Long id);
    
    public Role findByName(String name);
    
    public void update(Long id, Role Role);
    
    public void delete(Long id);
    
    public void put(Long id, Role role);
}
