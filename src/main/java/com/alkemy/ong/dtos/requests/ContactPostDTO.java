package com.alkemy.ong.dtos.requests;

import com.alkemy.ong.entities.Contact;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
public class ContactPostDTO {

    @NotEmpty
    private String name;

    private String phone;

    @NotEmpty
    private String email;

    @NotEmpty
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
