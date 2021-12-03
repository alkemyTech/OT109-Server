package com.alkemy.ong.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListMemberDTO {
    @NotNull
    private Long Id;

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


    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;
}
