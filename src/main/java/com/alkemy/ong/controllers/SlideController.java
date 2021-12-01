package com.alkemy.ong.controllers;

import com.alkemy.ong.pojos.output.SlideCompact;
import com.alkemy.ong.services.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/slides")
public class SlideController {

    @Autowired
    private SlideService slideService;

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
