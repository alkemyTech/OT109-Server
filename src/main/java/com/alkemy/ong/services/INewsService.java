package com.alkemy.ong.services;

import com.alkemy.ong.dtos.requests.NewPostPutRequestDTO;
import com.alkemy.ong.dtos.responses.NewDTO;
import com.alkemy.ong.exceptions.NotFoundException;

public interface INewsService {
    NewDTO saveNews(NewPostPutRequestDTO newPostRequestDTO) throws NotFoundException;
    NewDTO updateNews( Long id,NewPostPutRequestDTO newPutRequestDTO) throws NotFoundException;
    void delete(Long id) throws NotFoundException;
    NewDTO getById(Long id) throws NotFoundException;
}
