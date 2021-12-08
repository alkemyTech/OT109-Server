package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.responses.CommentDTO;
import com.alkemy.ong.entities.Comment;
import com.alkemy.ong.services.impl.CommentServiceImpl;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    private ResponseEntity<?> getAll(){
        List<CommentDTO> comments = commentService.findAll()
                .stream()
                .map(comment -> modelMapper.map(comment,CommentDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }

}
