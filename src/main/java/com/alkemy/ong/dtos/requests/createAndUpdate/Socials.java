package com.alkemy.ong.dtos.requests.createAndUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * De esta clase extienden la clase OrganizationEntity y Member
 */
@Data
@AllArgsConstructor
public abstract class Socials {
    @NotBlank
    private String name;
    /**
     * Puede ser null/blank o si hay un texto entonces debe ser un URI valido
     */
    @Nullable
    @Pattern(regexp = "((http|https)://)?(www.facebook.com)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)", message="Attribute facebookUrl is not url valid")
    private String facebookUrl;
    @Nullable
    @Pattern(regexp = "((http|https)://)?(www.instagram.com)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)", message="Attribute instagramUrl is not url valid")
    private String instagramUrl;
    @Nullable
    @Pattern(regexp = "((http|https)://)?(www.linkedin.com)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)", message="Attribute linkedinUrl is not a url valid")
    private String linkedinUrl;
}
