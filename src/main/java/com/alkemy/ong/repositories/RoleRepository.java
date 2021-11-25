package com.alkemy.ong.repositories;

import com.alkemy.ong.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
    
}
