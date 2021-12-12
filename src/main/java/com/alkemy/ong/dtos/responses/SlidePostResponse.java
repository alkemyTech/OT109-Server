package com.alkemy.ong.dtos.responses;

import lombok.Data;

@Data
public class SlidePostResponse {
    private String imageUrl;
    private String text;
    private Integer orderNum;
    private Long organization_id;


}
