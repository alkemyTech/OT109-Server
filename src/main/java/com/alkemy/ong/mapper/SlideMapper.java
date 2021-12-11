package com.alkemy.ong.mapper;

import com.alkemy.ong.dtos.requests.SlideRequest;
import com.alkemy.ong.dtos.responses.SlidePostResponse;
import com.alkemy.ong.entities.Slide;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SlideMapper {

    public List<SlideRequest> slideToSlideRequest(List<Slide> slides) {
        List<SlideRequest> slideRequests = new ArrayList<>();
        SlideRequest slideRequest;
        for (Slide slide : slides) {
            slideRequest = new SlideRequest();
            slideRequest.setImageUrl(slide.getImageUrl());
            slideRequest.setText(slide.getText());
            slideRequest.setOrderNum(slide.getOrderNum());
            slideRequest.setOrganization_id(slide.getOrganization().getId());
            slideRequests.add(slideRequest);
        }
        return slideRequests;
    }

    public SlidePostResponse slideEntity2PostResponse(Slide slide) {
        SlidePostResponse slideResponse = new SlidePostResponse();
        slideResponse.setImageUrl(slide.getImageUrl());
        slideResponse.setText(slide.getText());
        slideResponse.setOrderNum(slide.getOrderNum());
        slideResponse.setOrganization_id(slide.getOrganization().getId());
        return slideResponse;
    }
}
