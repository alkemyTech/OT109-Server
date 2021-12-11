package com.alkemy.ong.mapper;

import com.alkemy.ong.dtos.requests.CommentPostRequestDTO;
import com.alkemy.ong.dtos.responses.CommentDTO;
import com.alkemy.ong.entities.Comment;
import com.alkemy.ong.repositories.NewsRepository;
import com.alkemy.ong.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentDTO commentEntity2Dto(Comment entityCreated) {
        CommentDTO result = new CommentDTO();
        result.setId(entityCreated.getId());
        result.setBody(entityCreated.getBody());
        result.setNew_id(entityCreated.getNew_id().getId());
        result.setUser_id(entityCreated.getUser_id().getId());
        return result;
    }

    @Autowired
    NewsRepository newsRepository;
    @Autowired
    UserRepository userRepository;

    public Comment commentDto2Entity(CommentPostRequestDTO commentDTO) {
        Comment entity = new Comment();
        entity.setBody(commentDTO.getBody());
        entity.setUser_id(userRepository.findById(commentDTO.getUserId()).get());
        entity.setNew_id(newsRepository.findById(commentDTO.getNewId()).get());
        return entity;
    }
}
