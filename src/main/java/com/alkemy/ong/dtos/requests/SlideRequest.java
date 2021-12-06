package com.alkemy.ong.dtos.requests;

import lombok.Data;

@Data
public class SlideRequest {
    private String imageUrl;
    private String text;
    private Integer orderNum;
    private Long organization_id;
}
