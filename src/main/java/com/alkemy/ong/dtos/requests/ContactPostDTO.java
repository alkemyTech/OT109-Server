package com.alkemy.ong.dtos.requests;

import com.alkemy.ong.entities.Contact;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ContactPostDTO {

    private String name;
    private String phone;
    private String email;
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
