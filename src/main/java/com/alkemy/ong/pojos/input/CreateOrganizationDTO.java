package com.alkemy.ong.pojos.input;

import com.alkemy.ong.dtos.requests.Socials;
import com.alkemy.ong.entities.Member;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.*;
import org.springframework.lang.Nullable;

 /*
@Data
@Builder*/
/*
@AllArgsConstructor
@NoArgsConstructor*/
@Getter
@Setter
public class  CreateOrganizationDTO extends Socials {
    /*
    @NonNull
    @NotNull
    private String name;
    */
    @NotNull
    @Pattern(regexp = "((http|https)://)?(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)(.jpg|.png|.jpeg)", message="The image URL has invalid format")
    private String image;
    private String address;

    private Integer phone;
    
    @Email(message = "Email has invalid format")
    @NotNull
    private String email;
    @NotNull
    private String welcomeText;
    private String aboutUsText;
    //private List<Member> members;
    /*
    @Pattern(regexp = "((http|https)://)?(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)", message="Attribute facebookUrl is not valid")
    private String facebookUrl;
    @Pattern(regexp = "((http|https)://)?(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)", message="Attribute linkedinUrl is not valid")
    private String linkedinUrl;
    @Pattern(regexp = "((http|https)://)?(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)", message="Attribute instagramUrl is not valid")
    private String instagramUrl;*/

    public CreateOrganizationDTO(@NotBlank @NotNull String name, @Pattern(regexp = "((http|https)://)?(www.facebook.com)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)", message = "Attribute facebookUrl is not url valid") String facebookUrl, @Pattern(regexp = "((http|https)://)?(www.instagram.com)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)", message = "Attribute instagramUrl is not url valid") String instagramUrl, @Pattern(regexp = "((http|https)://)?(www.linkedin.com)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)", message = "Attribute linkedinUrl is not a url valid") String linkedinUrl, String image, String address, Integer phone, String email, String welcomeText, String aboutUsText) {
        super(name, facebookUrl, instagramUrl, linkedinUrl);
        this.image = image;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.welcomeText = welcomeText;
        this.aboutUsText = aboutUsText;
    }
    public CreateOrganizationDTO(){
        super(null,null,null,null);

    }
}
