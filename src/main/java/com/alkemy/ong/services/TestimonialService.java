package com.alkemy.ong.services;

import java.util.Optional;

import com.alkemy.ong.entities.TestimonialEntity;
import com.alkemy.ong.dtos.requests.TestimonialDTO;
import org.springframework.data.domain.Slice;

public interface TestimonialService {

    TestimonialEntity create(TestimonialDTO testimonialDto);

    TestimonialEntity update(Long id, TestimonialDTO testimonialDto);

    void delete(Long id);

    TestimonialEntity findById(Long id);

    Slice<TestimonialEntity> findAll(int page, int size);
}

    }

}