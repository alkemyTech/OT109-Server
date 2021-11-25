package com.alkemy.ong.repositories;

import com.alkemy.ong.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByDeletedAtIsNull();

    Optional<Category> findByName(String name);
}
