package com.alkemy.ong.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityDTO {
    private long id;
    private String name;
    private String content;
    private String image;
}
