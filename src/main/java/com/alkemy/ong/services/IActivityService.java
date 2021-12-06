package com.alkemy.ong.services;

import com.alkemy.ong.dtos.requests.ActivityPostPutRequestDTO;
import com.alkemy.ong.dtos.responses.ActivityDTO;
import com.alkemy.ong.entities.Activity;
import com.alkemy.ong.exceptions.NotFoundException;

public interface IActivityService {
    ActivityDTO create(ActivityPostPutRequestDTO activityPostRequestDTO);
    ActivityDTO update(Long id, ActivityPostPutRequestDTO activityPutRequestDTO) throws NotFoundException;
}
