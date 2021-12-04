package com.alkemy.ong.pojos.input;

import com.alkemy.ong.entities.Member;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrganizationDTO {
    
    @NonNull
    @NotNull
    private String name;
    
    @Pattern(regexp = "((http|https)://)?(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)(.jpg|.png|.jpeg)", message="The image URL has invalid format")
    private String image;
    private String address;
    private Integer phone;
    
    @Email(message = "Email has invalid format")
    private String email;
    private String welcomeText;
    private String aboutUsText;
    private List<Member> members;
}
