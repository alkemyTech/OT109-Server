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


}
