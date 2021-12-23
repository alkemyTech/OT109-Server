package com.alkemy.ong.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

@Getter
@Setter
/*
@AllArgsConstructor
@NoArgsConstructor*/
public class MemberRequest extends Socials{


    /*
    @Nullable
    @Pattern(regexp = "((http|https)://)?(www.facebook.com)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)", message="Attribute facebookUrl is not url valid")
    private String facebookUrl;
    @Nullable
    @Pattern(regexp = "((http|https)://)?(www.instagram.com)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)", message="Attribute instagramUrl is not url valid")
    private String instagramUrl;
    @Nullable
    @Pattern(regexp = "((http|https)://)?(www.linkedin.com)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)", message="Attribute linkedinUrl is not a url valid")
    private String linkedinUrl;*/
    @Nullable
    private String image;
    @NotNull @NotBlank
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
