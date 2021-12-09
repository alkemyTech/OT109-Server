package com.alkemy.ong.repositories;

import com.alkemy.ong.entities.Role;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    
    @Modifying
    @Query(value = "INSERT INTO roles (id) VALUES (:id)", nativeQuery = true)
    @Transactional
    void putId(@Param("id") Long id);
    
    Role findByName(String name);
}
