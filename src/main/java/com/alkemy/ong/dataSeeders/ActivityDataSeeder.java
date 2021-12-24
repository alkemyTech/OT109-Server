package com.alkemy.ong.dataSeeders;

import com.alkemy.ong.entities.Activity;
import com.alkemy.ong.services.impl.ActivityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ActivityDataSeeder implements CommandLineRunner {

    @Autowired
    ActivityService activityService;

    @Override
    public void run(String... args) throws Exception {

        Activity activity = new Activity();
        activity.setName("name");
        activity.setContent("content");
        activity.setImage("image");
        if (!activityService.existsByName(activity.getName())) {
            activityService.create(activity);
        }

    }

}
