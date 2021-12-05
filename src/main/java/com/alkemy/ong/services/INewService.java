package com.alkemy.ong.services;

import com.alkemy.ong.dtos.requests.NewPostPutRequestDTO;
import com.alkemy.ong.dtos.responses.NewDTO;
import com.alkemy.ong.exceptions.NotFoundException;

public interface INewService {
    NewDTO saveNews(NewPostPutRequestDTO newPostRequestDTO);
    NewDTO updateNews( Long id,NewPostPutRequestDTO newPutRequestDTO) throws NotFoundException;
    void delete(Long id);

}
