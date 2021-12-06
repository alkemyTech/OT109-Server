package com.alkemy.ong.services;

import com.alkemy.ong.dtos.requests.ActivityPostPutRequestDTO;
import com.alkemy.ong.dtos.responses.ActivityDTO;
import com.alkemy.ong.entities.Activity;
import com.alkemy.ong.exceptions.ActivityServiceException;
import com.alkemy.ong.exceptions.BadRequestException;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.repositories.ActivityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService  implements IActivityService{

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Activity create(Activity activity){
        activity.setCreatedAt(new Date());
        return activityRepository.save(activity);
    }

    public Activity update(Activity activity){
        activity.setUpdatedAt(new Date());
        return activityRepository.save(activity);
    }

    public Activity findById(Long id){
        return activityRepository.findById(id).orElseThrow( () -> (new ActivityServiceException("Activity with id: " + id + " not found.")));
    }

    public Activity findByName(String name){
        return activityRepository.findByName(name).orElseThrow( () -> (new ActivityServiceException("Activity with name: " + name + " not found.")));
    }

    public Activity delete(Long id){
        Activity activityToSoftDelete = this.findById(id);
        activityToSoftDelete.setDeletedAt(new Date());
        return activityRepository.save(activityToSoftDelete);
    }

    public List<Activity> findAll(){
        return activityRepository.findByDeletedAtIsNull();
    }


    @Override
    public ActivityDTO create(ActivityPostPutRequestDTO activityPostRequestDTO) {
        if (activityPostRequestDTO.getName().isBlank()) {
            throw new BadRequestException("Name may not be empty");
        }
        if(activityPostRequestDTO.getContent().isBlank()){
            throw new BadRequestException("Content may not be empty");
        }

        Activity activity = modelMapper.map(activityPostRequestDTO, Activity.class);
        activity.setCreatedAt(new Date());
        Activity activityCreated = activityRepository.save(activity);
        ActivityDTO result = modelMapper.map(activityCreated, ActivityDTO.class);
        return result;
    }

    @Override
    public ActivityDTO update(Long id, ActivityPostPutRequestDTO activityPutRequestDTO) throws NotFoundException {
        Activity activityOptional = activityRepository.findById(id).orElseThrow(() -> new NotFoundException("Activity does not exist"));
        activityOptional.setName(activityPutRequestDTO.getName());
        activityOptional.setContent(activityPutRequestDTO.getContent());
        activityOptional.setImage(activityPutRequestDTO.getImage());
        activityOptional.setUpdatedAt(new Date());
        Activity activitySave = activityRepository.save(activityOptional);
        ActivityDTO activityDTO = modelMapper.map(activitySave, ActivityDTO.class);
        return activityDTO;
    }
}
