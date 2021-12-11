package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.ActivityPostPutRequestDTO;
import com.alkemy.ong.dtos.responses.ActivityDTO;
import com.alkemy.ong.exceptions.BadRequestException;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.services.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/activities")
public class ActivityController {
    @Autowired
    private IActivityService activityService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ActivityPostPutRequestDTO activityPostRequestDTO){
        try{
            ActivityDTO activityDTO = activityService.create(activityPostRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(activityDTO);
        }catch (BadRequestException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody ActivityPostPutRequestDTO activityPutRequestDTO ){
        try{
            ActivityDTO activityDTO = activityService.update(id,activityPutRequestDTO);
            return ResponseEntity.ok().body(activityDTO);
        }catch (BadRequestException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }catch (NotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

    }

}
