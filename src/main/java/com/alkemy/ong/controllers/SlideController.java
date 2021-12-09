package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.SlideRequest;
import com.alkemy.ong.entities.OrganizationEntity;
import com.alkemy.ong.entities.Slide;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.pojos.output.SlideCompact;
import com.alkemy.ong.pojos.output.SlideRequestUpdate;
import com.alkemy.ong.services.SlideService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/slides")
public class SlideController {

    @Autowired
    private SlideService slideService;

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/{id}")
    public String delete(@PathVariable("id") Long id) throws NotFoundException {
        slideService.delete(id);
        return "Post with id " + id + " is deleted";
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/{id}")
    public Slide update(@PathVariable("id") Long id, @RequestBody SlideRequest slideRequestUpdate) throws NotFoundException {
        return slideService.update(id,slideRequestToSlide(slideRequestUpdate));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Slide save(@RequestBody SlideRequest slideRequest) throws NotFoundException {
        return slideService.save(slideRequestToSlide(slideRequest));
    }

    private Slide slideRequestToSlide(SlideRequest slideRequest){
        Slide slideEntity = new ModelMapper().map(slideRequest,Slide.class);
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setId(slideRequest.getOrganization_id());
        slideEntity.setOrganization(organizationEntity);
        return slideEntity;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{id}")
    public Slide getById(@PathVariable("id") Long id) throws NotFoundException {
        return slideService.getById(id);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handle(NotFoundException notFoundException) {
        return notFoundException.getMessage();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public List<SlideCompact> getSlides(){
        return slideService.getAll()
                .stream().map(slide -> new SlideCompact(
                        slide.getImageUrl(),
                        slide.getOrderNum()))
                .collect(Collectors.toList());
    }
}
