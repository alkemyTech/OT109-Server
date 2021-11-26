package com.alkemy.ong.repositories;

import com.alkemy.ong.entities.User;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    @Modifying
    @Query(value = "INSERT INTO users (id) VALUES (:id)", nativeQuery = true)
    @Transactional
    void putId(@Param("id") Long id);
    
    Optional<User> findByEmailIgnoreCase(String email);
    
    Optional<User> findByEmail(String email);
    
    List<User> findByRole_Name(String name);
    
    List<User> findByRole_Id(Long id);
}
