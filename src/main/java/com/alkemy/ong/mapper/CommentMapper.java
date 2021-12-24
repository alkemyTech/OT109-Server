package com.alkemy.ong.mapper;

import com.alkemy.ong.dtos.requests.createAndUpdate.CommentPostRequestDTO;
import com.alkemy.ong.dtos.responses.CommentDTO;
import com.alkemy.ong.entities.Comment;
import com.alkemy.ong.exceptions.NotFoundException;
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
        result.setNews_id(entityCreated.getNews().getId());
        result.setUser_id(entityCreated.getUser().getId());
        return result;
    }

    @Autowired
    NewsRepository newsRepository;
    @Autowired
    UserRepository userRepository;

    public Comment commentDto2Entity(CommentPostRequestDTO commentDTO) {
        Comment entity = new Comment();
        entity.setBody(commentDTO.getBody());
        entity.setUser(userRepository.findById(commentDTO.getUser_id()).orElseThrow( () -> new NotFoundException("User not found.")));
        entity.setNews(newsRepository.findById(commentDTO.getNews_id()).orElseThrow( () -> new NotFoundException("News not found." )));
        return entity;
    }
}
