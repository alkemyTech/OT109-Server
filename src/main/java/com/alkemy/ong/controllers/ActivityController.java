package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.ActivityPostPutRequestDTO;
import com.alkemy.ong.dtos.responses.ActivityDTO;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ActivityDTO create(@Valid @RequestBody ActivityPostPutRequestDTO activityPostRequestDTO){

        ActivityDTO activityDTO = activityService.create(activityPostRequestDTO);
        return activityDTO;

    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public void update(@PathVariable Long id,
                                    @Valid @RequestBody ActivityPostPutRequestDTO activityPutRequestDTO ){

            ActivityDTO activityDTO = activityService.update(id,activityPutRequestDTO);
            return ResponseEntity.ok().body(activityDTO);

    }

}
