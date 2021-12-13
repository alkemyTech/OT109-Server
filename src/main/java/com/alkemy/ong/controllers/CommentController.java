package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.CommentPostRequestDTO;
import com.alkemy.ong.dtos.requests.CommentPutRequestDTO;
import com.alkemy.ong.dtos.responses.CommentDTO;
import com.alkemy.ong.dtos.responses.CommentListDTO;
import com.alkemy.ong.entities.Comment;
import com.alkemy.ong.services.CommentService;
import com.alkemy.ong.util.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/comments")
    public ResponseEntity<CommentDTO> create(@Valid @RequestBody CommentPostRequestDTO commentPostRequestDTO) {
        CommentDTO commentCreated = commentService.create(commentPostRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentCreated);
    }

    @GetMapping("/comments")
    private ResponseEntity<?> getAll(){
        List<CommentDTO> comments = commentService.findAll()
                .stream()
                .map(comment -> modelMapper.map(comment,CommentDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CommentPutRequestDTO request, HttpServletRequest header){
        String token = header.getHeader("Authorization");
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

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<String> delete(@PathVariable @Min(value = 1, message = "Comment id cannot be less than one") Long id, HttpServletRequest header){
        if(!commentService.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found.");
        }
        String token = header.getHeader("Authorization");
        String email = jwtUtil.extractUserEmail(token.substring(7));
        List<String> roles = jwtUtil.extractRoles(token.substring(7));

        if(commentService.validUser(email, id) || isAdmin(roles)){
            commentService.deleteById(id);
            return ResponseEntity.ok("Comment id: " + id + " deleted.");
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(email + "is not authorized to delete this comments.");
        }
    }

}
