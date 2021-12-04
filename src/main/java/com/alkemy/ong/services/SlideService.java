package com.alkemy.ong.services;

import com.alkemy.ong.entities.Slide;
import com.alkemy.ong.exceptions.NotFoundException;

import java.util.List;

public interface SlideService {
    Slide save(Slide slide) throws NotFoundException;
    Slide getById(Long id) throws NotFoundException;
    void delete(Long id) throws NotFoundException;
    Slide update(Long id, Slide slide) throws NotFoundException;
    List<Slide> getAll();
    void deleteAll();
}
