package com.alkemy.ong.pojos.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListOrganizationDTO {
    
    private String name;
    private String image;
    private Integer phone;
    private String address;
    
}
