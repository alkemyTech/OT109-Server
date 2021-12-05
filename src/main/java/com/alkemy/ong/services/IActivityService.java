package com.alkemy.ong.services;

import com.alkemy.ong.dtos.requests.ActivityPostPutRequestDTO;
import com.alkemy.ong.dtos.responses.ActivityDTO;
import com.alkemy.ong.entities.Activity;

public interface IActivityService {
    ActivityDTO create(ActivityPostPutRequestDTO activityPostRequestDTO);
    ActivityDTO update(Long id, ActivityPostPutRequestDTO activityPutRequestDTO);
}
