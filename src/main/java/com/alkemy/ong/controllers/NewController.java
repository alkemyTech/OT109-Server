package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.NewPostPutRequestDTO;
import com.alkemy.ong.dtos.responses.NewDTO;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.services.INewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/news")
public class NewController {
    @Autowired
    private INewService newService;
    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody NewPostPutRequestDTO newPostRequestDTO) throws NotFoundException {
        try{
            NewDTO newDTO = newService.saveNews(newPostRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newDTO);
        }catch (NotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update( @PathVariable Long id,@Valid @RequestBody NewPostPutRequestDTO newPostRequestDTO) throws NotFoundException {

        try{
            NewDTO newDTO = newService.updateNews(id,newPostRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(newDTO);
        }catch (NotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws NotFoundException {
        try{
            newService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (NotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }


    }


}
