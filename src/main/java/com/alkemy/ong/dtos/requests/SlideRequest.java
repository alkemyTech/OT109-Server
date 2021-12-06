package com.alkemy.ong.dtos.requests;

import com.alkemy.ong.entities.OrganizationEntity;
import lombok.Data;

@Data
public class SlideRequest {
    private String imageUrl;
    private String text;
    private Integer orderNum;
    private OrganizationEntity organization;
}
