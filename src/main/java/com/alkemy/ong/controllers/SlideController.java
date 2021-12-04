package com.alkemy.ong.controllers;

import com.alkemy.ong.entities.Slide;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.pojos.output.SlideCompact;
import com.alkemy.ong.pojos.output.SlideDetails;
import com.alkemy.ong.pojos.output.SlideRequestUpdate;
import com.alkemy.ong.services.SlideService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/slides")
public class SlideController {

    @Autowired
    private SlideService slideService;

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) throws NotFoundException {
        slideService.delete(id);
        return new ResponseEntity<>( "Post with id " + id + " is deleted", HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Slide> update(@PathVariable("id") Long id, @RequestBody SlideRequestUpdate slideRequestUpdate) throws NotFoundException {
        return new ResponseEntity<>(slideService.update(id,new ModelMapper().map(slideRequestUpdate,Slide.class)), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Slide> save(@RequestBody Slide slide) throws NotFoundException {
        //cuando se le da a la slide una organizacion con un id no registrado en la base de datos devuelve
        //una excepcion correspondiente a ParamNotFound
        return new ResponseEntity<>(slideService.save(slide), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<SlideDetails> getById(@PathVariable("id") Long id) throws NotFoundException {
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(slideService.getById(id), SlideDetails.class), HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleExistsByEmail(NotFoundException existsWithEmailException) {
        return new ResponseEntity<>(existsWithEmailException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping()
    public ResponseEntity<List<SlideCompact>> getSlides(){
        return new ResponseEntity<>(
                slideService.getAll()
                    .stream().map(slide -> new SlideCompact(
                            slide.getImageUrl(),
                            slide.getOrderNum()))
                    .collect(Collectors.toList()),
                HttpStatus.OK);
    }
}
