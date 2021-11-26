package com.alkemy.ong.services.impl;

import com.alkemy.ong.entities.Slide;
import com.alkemy.ong.repositories.SlideRepository;
import com.alkemy.ong.services.SlideService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SlideServiceImpl implements SlideService {

    @Autowired
    private SlideRepository slideRepository;

    /**
     * Buscamos en la base de datos el mismo OrderNum de la slide dada,Si existe entonces
     * A a la slide que tenga ese OrderNum le damos un OrderNum superior a todas las demas slides,
     * y la slide dada se queda con su OrderNum seteada ocupando el "lugar" de la anterior slide
     * @param slide
     * @return
     */
    @Override
    public Slide save(Slide slide) {
        try{
            Slide slideBD = slideRepository.getByOrderNum(slide.getOrderNum());
            slideBD.setOrderNum(slideRepository.getByMaxOrderNum().getOrderNum()+1);
            slideRepository.save(slideBD);
        }catch (Exception ex){
            //Esta excepcion ocurre cuando no hay ningun slide con un orderNum dado
        }finally {
            return slideRepository.save(slide);
        }
    }

    @Override
    public Slide getById(Long id) throws NotFoundException {
        try {
            return slideRepository.findById(id).get();
        }catch (NoSuchElementException ex){
            throw new NotFoundException("Slide with id " + id + " not found");
        }
    }

    /**
     * Cuando eliminamos un Slide decremetamos los orderNum mayores al id del slide eliminado
     *
     * @param id pertenece a un id de un Slide
     * @throws NotFoundException el id dado no existe para ningun Slide
     */
    @Override
    public void delete(Long id) throws NotFoundException {
        try {
            slideRepository.deleteById(id);
            slideRepository.decreaseHigherThan(id);
        }catch (EmptyResultDataAccessException ex){
            throw new NotFoundException("Slide with id " + id + " not found");
        }
    }

    @Override
    public Slide update(Long id, Slide slide) throws NotFoundException {
       slide.setId(id);
       return save(slide);
    }

    @Override
    public List<Slide> getAll() {
        return slideRepository.findAll();
    }

    @Override
    public void deleteAll() {
        slideRepository.deleteAll();
    }

}
