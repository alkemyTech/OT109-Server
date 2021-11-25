package com.alkemy.ong.repositories;

import com.alkemy.ong.entities.Slide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlideRepository extends JpaRepository<Slide,Long> {
}
