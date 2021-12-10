package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.responses.CommentDTO;
import com.alkemy.ong.entities.Comment;
import com.alkemy.ong.entities.Role;
import com.alkemy.ong.services.impl.CommentServiceImpl;
import com.alkemy.ong.util.JwtUtil;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private JwtUtil jwtUtil;

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

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CommentDTO request, @RequestHeader("Authorization") String token){

        String email = jwtUtil.extractUserEmail(token.substring(7));
        List<String> roles = jwtUtil.extractRoles(token.substring(7));

        if(commentService.validUser(email,id) || isAdmin(roles)){
            Comment comment = modelMapper.map(request,Comment.class);
            comment = commentService.update(comment,id);
            return ResponseEntity.ok(modelMapper.map(comment,CommentDTO.class));
        }else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(String.format("%s unauthorized to edit this comment",email));
    }

    private boolean isAdmin(List<String> roles){
        return roles.contains("ADMIN");
    }

}
