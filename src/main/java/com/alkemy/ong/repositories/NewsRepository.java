package com.alkemy.ong.repositories;

import com.alkemy.ong.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository @Transactional
public interface NewsRepository extends JpaRepository<News,Long> {
}
