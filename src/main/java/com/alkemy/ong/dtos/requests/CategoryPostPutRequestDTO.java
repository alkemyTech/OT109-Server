package com.alkemy.ong.dtos.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class CategoryPostPutRequestDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @Nullable
    @Pattern(regexp = "((http|https)://)?(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)(.jpg|.png|.jpeg)", message="The image URL has invalid format")
    private String image;
}
