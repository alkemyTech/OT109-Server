package com.alkemy.ong.services;

import com.alkemy.ong.dtos.requests.createAndUpdate.ActivityPostPutRequestDTO;
import com.alkemy.ong.dtos.responses.ActivityDTO;
import com.alkemy.ong.exceptions.BadRequestException;
import com.alkemy.ong.exceptions.NotFoundException;

public interface IActivityService {
    ActivityDTO create(ActivityPostPutRequestDTO activityPostRequestDTO) throws BadRequestException;
    ActivityDTO update(Long id, ActivityPostPutRequestDTO activityPutRequestDTO) throws NotFoundException;
}
