package com.alkemy.ong.services;

import com.alkemy.ong.dtos.requests.NewsPostPutRequestDTO;
import com.alkemy.ong.dtos.responses.NewsDTO;
import com.alkemy.ong.entities.News;
import com.alkemy.ong.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface INewsService {
    NewsDTO saveNews(NewsPostPutRequestDTO newsPostRequestDTO) throws NotFoundException;
    NewsDTO updateNews(Long id, NewsPostPutRequestDTO newsPutRequestDTO) throws NotFoundException;
    void delete(Long id) throws NotFoundException;
    NewsDTO getById(Long id) throws NotFoundException;
    Page<News> getAll(Pageable page);
}
