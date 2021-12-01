package com.alkemy.ong.pojos.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestOrganizationDTO {
    
    private String name;
    private String image;
    private Integer phone;
    private String address;
    
}
