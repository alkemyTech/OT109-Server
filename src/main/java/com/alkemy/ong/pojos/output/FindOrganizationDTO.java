package com.alkemy.ong.pojos.output;

import com.alkemy.ong.dtos.requests.createAndUpdate.SlideRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindOrganizationDTO {

    private String name;
    private String image;
    private Integer phone;
    private String address;
    private String facebookUrl;
    private String linkedinUrl;
    private String instagramUrl;
    private List<SlideRequest> slide;
}
