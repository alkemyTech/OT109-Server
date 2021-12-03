package com.alkemy.ong.dtos.responses;

import com.alkemy.ong.entities.OrganizationEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDTO {

    @NotNull
    private Long id;
    @NotBlank
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
    @Valid
    @NotNull
    private OrganizationEntity organization;
}
