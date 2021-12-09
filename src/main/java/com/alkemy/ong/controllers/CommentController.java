package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.responses.CommentListDTO;
import com.alkemy.ong.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("posts/{id}/comments")
    public ResponseEntity<?> findCommentsByNewsId (@PathVariable @Min(value = 1, message = "Id must be equal or greater than 1")Long id){
        List<CommentListDTO> commentList = commentService.findCommentsByNewsId(id);
        if(commentList.isEmpty()){
            return new ResponseEntity<>("There are not comments related to that news_id.",HttpStatus.NOT_FOUND);
        }
        return new  ResponseEntity<>(commentList,HttpStatus.OK);
    }
}
