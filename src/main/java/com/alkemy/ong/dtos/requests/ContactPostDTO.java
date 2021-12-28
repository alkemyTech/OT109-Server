package com.alkemy.ong.dtos.requests;

import com.alkemy.ong.entities.Contact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ContactPostDTO {

    @NotBlank
    @Size(min = 3, message = "Name should be at least three characters")
    private String name;
    @NotBlank
    @Size(min = 10, message = "Phone number should be at least nine characters")
    private String phone;
    @NotBlank
    @Email(message = "Email is not valid", regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    private String email;
    @NotBlank
    private String message;

    public Contact toContact(){
        Contact contactToCreate = new Contact();
        contactToCreate.setName(this.getName());
        contactToCreate.setPhone(this.getPhone());
        contactToCreate.setEmail(this.getEmail());
        contactToCreate.setMessage(this.getMessage());
        return contactToCreate;
    }

}
