package com.alkemy.ong.services.impl;

import com.alkemy.ong.entities.Slide;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.repositories.SlideRepository;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.services.OrganizationService;
import com.alkemy.ong.services.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SlideServiceImpl implements SlideService {

    @Autowired
    private SlideRepository slideRepository;

    @Autowired
    private OrganizationService organizationService;

    /**
     * Si el slide dado contiene un orderNum sin valor(null) le otorgamos un OrderNum mayor a todos los demas slides
     * para que este se visualice ultimo.
     *
     * Buscamos en la base de datos el mismo OrderNum de la slide dada,Si existe entonces
     * A a la slide que tenga ese OrderNum le damos un OrderNum superior a todas las demas slides,
     * y la slide dada se queda con su OrderNum seteada ocupando el "lugar" de la anterior slide
     * @param slide a guardar
     * @return Slide con su id registrado
     * @throws NotFoundException Cuando la OrganizacionEntity de el Slide dado no tiene un id registrado en la base de dato
     */
    @Override
    public Slide save(Slide slide) throws NotFoundException {
        validateSlide(slide);
        if(slideRepository.existsByOrderNum(slide.getOrderNum())){
            Slide slideBD = slideRepository.getByOrderNum(slide.getOrderNum());
            slideBD.setOrderNum(slideRepository.getByMaxOrderNum()+1);
            slideRepository.save(slideBD);
        }
        return slideRepository.save(slide);
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
     * Cuando eliminamos un Slide decremetamos los orderNum mayores al orderNum del slide eliminado
     *
     * @param id pertenece a un id de un Slide
     * @throws NotFoundException el id dado no existe para ningun Slide
     */
    @Override
    public void delete(Long id) throws NotFoundException {
        try {
            int orderNumFromEntity = getById(id).getOrderNum();
            slideRepository.deleteById(id);
            slideRepository.decreaseHigherThan(orderNumFromEntity);

        }catch (EmptyResultDataAccessException ex){
            throw new NotFoundException("Slide with id " + id + " not found");
        }
    }

    @Override
    public Slide update(Long id, Slide slide) throws NotFoundException {
        Slide slideBD = getById(id);
        //Todos los los valores serian slideBD.setAtributte(slide.getAtributte)
        slide.setId(id);
        return save(slide);
    }

    @Override
    public List<Slide> getAll() {
        return slideRepository.findByOrderByOrderNumAsc();
    }

    @Override
    public void deleteAll() {
        slideRepository.deleteAll();
    }

    private void validateSlide(Slide slide)throws ParamNotFound {
        if(slide.getImageUrl() == null || slide.getText() == null){
            throw new ParamNotFound("Atributtes can't be null");
        }
        if(slide.getOrderNum() == null){
            slide.setOrderNum(slideRepository.getByMaxOrderNum()+1);
        }
        if(slide.getOrderNum()<0){
            throw new ParamNotFound("Atributte *orderNum* only admits values greater than or equal to 0 ");
        }
        if(!slide.getImageUrl().endsWith(".png") && !slide.getImageUrl().endsWith(".jpg")) {
            throw new ParamNotFound("Atributte *imageUrl* only supports a path that supports strings ending with .png or .jpg ");
        }
        if(slide.getText().isBlank()){
            throw new ParamNotFound("Atributte *text* is empty");
        }
        Long organization_id = slide.getOrganization().getId();
        if(organizationService.findById(organization_id)== null){
            throw new NotFoundException("Organization with id " + organization_id + " not found");
        }
    }
}
