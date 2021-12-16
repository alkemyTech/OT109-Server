package com.alkemy.ong.repositories;

import com.alkemy.ong.entities.TestimonialEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TestimonialRepository extends JpaRepository<TestimonialEntity, Long> {

    public Slice<TestimonialEntity> findBy(Pageable pageable);
}
