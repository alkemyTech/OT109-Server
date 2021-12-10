package com.alkemy.ong.services.impl;

import com.alkemy.ong.entities.Comment;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.repositories.CommentRepository;
import com.alkemy.ong.repositories.NewsRepository;
import com.alkemy.ong.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    NewsRepository newsRepository;

    @Override
    public Comment create(Comment comment) throws NotFoundException {
        if(!newsRepository.existsById(comment.getNew_id().getId())){
            throw new NotFoundException("News Not Found.");
        }
        comment.setBody(comment.getBody());
        comment.setUser_id(comment.getUser_id());
        comment.setNew_id(comment.getNew_id());
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAll() throws NotFoundException {
        return commentRepository.findByOrderByCreatedAtDesc();
    }

    @Override
    public Comment findById(Long id) throws NotFoundException {
        if(!commentRepository.existsById(id)){
            throw new NotFoundException("Comment Not Found.");
        }
        return commentRepository.findById(id).get();
    }

    @Override
    public Comment update(Comment comment, Long id) throws NotFoundException {

        Comment uptComment= commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Member does not exist"));;

        uptComment.setBody(comment.getBody());
        return commentRepository.save(uptComment);
    }

    @Override
    public void deleteById(Long id) throws NotFoundException {
        commentRepository.deleteById(id);
    }

    @Override
    public Boolean validUser(String email,Long commentId) {
        Optional<String> owner = commentRepository.findOwnerEmail(commentId);
        return owner.map(s -> s.equals(email)).orElse(false);
    }
}
