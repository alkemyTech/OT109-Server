package com.alkemy.ong.pojos.output;

import com.alkemy.ong.dtos.requests.SlideRequest;
import com.alkemy.ong.entities.Slide;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindOrganizationDTO {
    
    private String name;
    private String image;
    private Integer phone;
    private String address;
    private List<SlideRequest> slides;
    
}
