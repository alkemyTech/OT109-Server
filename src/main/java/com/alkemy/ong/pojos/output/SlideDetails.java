package com.alkemy.ong.pojos.output;

import com.alkemy.ong.entities.OrganizationEntity;
import lombok.Data;

@Data
public class SlideDetails {
    private Long id;
    private String imageUrl;
    private String text;
    private int orderNum;
}
