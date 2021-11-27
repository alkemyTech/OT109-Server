package com.alkemy.ong.services;

import com.alkemy.ong.entities.Comment;
import com.alkemy.ong.exceptions.DataAlreadyExistException;
import com.alkemy.ong.exceptions.NotFoundException;

import java.util.List;

public interface CommentService {
    Comment create(Comment comment) throws NotFoundException;
    List<Comment> findAll() throws NotFoundException;
    Comment findById(Long id) throws NotFoundException;;
    Comment update(Comment comment, Long id) throws NotFoundException;;
    void deleteById(Long id) throws NotFoundException;;

}