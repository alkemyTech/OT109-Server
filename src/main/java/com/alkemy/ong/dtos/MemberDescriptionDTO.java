package com.alkemy.ong.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDescriptionDTO {

    @NotNull
    private Long id;
    @NotNull
    private String name;
    @Nullable
    private String facebookUrl;
    @Nullable
    private String instagramUrl;
    @Nullable
    private String linkedinUrl;
    @Nullable
    private String image;
    @NotNull
    private String description;
    //@NotNull
    //private OrganizationEntity organization
}
