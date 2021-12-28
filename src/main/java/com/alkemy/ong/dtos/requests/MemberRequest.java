package com.alkemy.ong.dtos.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class MemberRequest extends Socials{
    @Nullable
    @Pattern(regexp = "((http|https)://)?(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)(.jpg|.png|.jpeg)", message="The image URL has invalid format")
    private String image;
    @NotBlank
    private String description;
    @NotNull
    private Long organizationId;

    public MemberRequest(@NotBlank @NotNull String name, @Pattern(regexp = "((http|https)://)?(www.facebook.com)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)", message = "Attribute facebookUrl is not url valid") String facebookUrl, @Pattern(regexp = "((http|https)://)?(www.instagram.com)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)", message = "Attribute instagramUrl is not url valid") String instagramUrl, @Pattern(regexp = "((http|https)://)?(www.linkedin.com)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)", message = "Attribute linkedinUrl is not a url valid") String linkedinUrl, @Nullable String image, String description, Long organizationId) {
        super(name, facebookUrl, instagramUrl, linkedinUrl);
        this.image = image;
        this.description = description;
        this.organizationId = organizationId;
    }
}
