package com.alkemy.ong.dtos.requests.createAndUpdate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
    @Nullable
    @Pattern(regexp = "((http|https)://)?(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)(.jpg|.png|.jpeg)", message="The image URL has invalid format")
    private String image;
    private String address;

    private Integer phone;
    
    @Email(message = "Email has invalid format")
    @NotBlank
    private String email;
    @NotBlank
    private String welcomeText;
    private String aboutUsText;
    //private List<Member> members;

    public CreateOrganizationDTO(){
        super(null,null,null,null);

    }
}
