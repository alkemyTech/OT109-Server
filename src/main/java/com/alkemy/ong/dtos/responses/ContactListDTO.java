package com.alkemy.ong.dtos.responses;

import com.alkemy.ong.entities.Contact;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class ContactListDTO {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private Date created_at;

    public ContactListDTO(Contact contact){
        this.id = contact.getId();
        this.name = contact.getName();
        this.phone = contact.getPhone();
        this.email = contact.getEmail();
        this.created_at = contact.getCreatedAt();
    }
}
