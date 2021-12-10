package com.alkemy.ong.dtos.requests;

import com.alkemy.ong.entities.Slide;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SlideRequest {
    private String imageUrl;
    private String text;
    private Integer orderNum;
    private Long organization_id;

    public static List<SlideRequest> slideToSlideRequest(List<Slide> slides) {
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
}
