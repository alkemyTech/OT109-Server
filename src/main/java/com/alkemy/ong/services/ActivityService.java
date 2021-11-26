package com.alkemy.ong.services;

import com.alkemy.ong.entities.Activity;
import com.alkemy.ong.exceptions.ActivityServiceException;
import com.alkemy.ong.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

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


}
