package com.alkemy.ong.dtos.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.*;

/**
 * Se usa en patch
 */
@Getter
@Setter
public class UpdateOrganizationDTO extends Socials{
    @Pattern(regexp = "((http|https)://)?(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)(.jpg|.png|.jpeg)", message="Invalid image url")
    private String image;
    private Integer phone;
    private String address;

    public UpdateOrganizationDTO(@NotBlank @NotNull String name, @Pattern(regexp = "((http|https)://)?(www.facebook.com)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)", message = "Attribute facebookUrl is not url valid") String facebookUrl, @Pattern(regexp = "((http|https)://)?(www.instagram.com)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)", message = "Attribute instagramUrl is not url valid") String instagramUrl, @Pattern(regexp = "((http|https)://)?(www.linkedin.com)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)", message = "Attribute linkedinUrl is not a url valid") String linkedinUrl, String image, Integer phone, String address) {
        super(name, facebookUrl, instagramUrl, linkedinUrl);
        this.image = image;
        this.phone = phone;
        this.address = address;
    }
}
