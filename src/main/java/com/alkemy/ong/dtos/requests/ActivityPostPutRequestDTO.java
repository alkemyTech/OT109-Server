package com.alkemy.ong.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityPostPutRequestDTO {
    private String name;
    private String content;
    private String image;
}
