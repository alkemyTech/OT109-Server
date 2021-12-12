package com.alkemy.ong.services;

import com.alkemy.ong.dtos.responses.CommentListDTO;
import com.alkemy.ong.dtos.requests.CommentPostRequestDTO;
import com.alkemy.ong.dtos.responses.CommentDTO;
import com.alkemy.ong.entities.Comment;
import com.alkemy.ong.exceptions.DataAlreadyExistException;
import com.alkemy.ong.exceptions.NotFoundException;

import java.util.List;

public interface CommentService {
    CommentDTO create(CommentPostRequestDTO commentDTO);
    List<Comment> findAll() throws NotFoundException;
    Comment findById(Long id) throws NotFoundException;
    Comment update(Comment comment, Long id) throws NotFoundException;
    void deleteById(Long id) throws NotFoundException;
    List<CommentListDTO> findCommentsByNewsId(Long id);
}
