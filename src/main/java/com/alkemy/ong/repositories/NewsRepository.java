package com.alkemy.ong.repositories;

import com.alkemy.ong.entities.Category;
import com.alkemy.ong.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository @Transactional
public interface NewsRepository extends JpaRepository<News,Long> {
    @Query(value = "from Category c where c.name = :name")
    Category findCategoryByName(@Param("name") String name);
}
