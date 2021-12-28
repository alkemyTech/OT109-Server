package com.alkemy.ong.services;

import com.alkemy.ong.dtos.requests.NewPostPutRequestDTO;
import com.alkemy.ong.dtos.responses.NewDTO;
import com.alkemy.ong.entities.News;
import com.alkemy.ong.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface INewsService {
    NewsDTO saveNews(NewPostPutRequestDTO newPostRequestDTO) throws NotFoundException;
    NewsDTO updateNews(Long id, NewPostPutRequestDTO newPutRequestDTO) throws NotFoundException;
    void delete(Long id) throws NotFoundException;
    NewsDTO getById(Long id) throws NotFoundException;
    Page<News> getAll(Pageable page);
}
