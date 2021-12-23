package com.alkemy.ong.dtos.responses;

import lombok.Data;

@Data
public class SlideRequestUpdate {
    private String imageUrl;
    private String text;
    private int orderNum;
}
