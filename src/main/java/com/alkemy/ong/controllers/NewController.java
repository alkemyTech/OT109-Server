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
    public ResponseEntity<NewDTO> create(@Valid @RequestBody NewPostPutRequestDTO newPostRequestDTO){
        NewDTO newDTO = newService.saveNews(newPostRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewDTO> update( @PathVariable Long id,@Valid @RequestBody NewPostPutRequestDTO newPostRequestDTO) throws NotFoundException {
        NewDTO newDTO = newService.updateNews(id,newPostRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(newDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        newService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
