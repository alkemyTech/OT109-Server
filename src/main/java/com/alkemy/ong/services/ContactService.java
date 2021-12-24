package com.alkemy.ong.services;

import com.alkemy.ong.dtos.responses.ContactListDTO;
import com.alkemy.ong.entities.Contact;
import java.util.List;
import org.springframework.stereotype.Service;

public interface ContactService {
    
    public List<ContactListDTO> findAll();
    
    public Contact findById(Long id);
    
    public Contact create(Contact contact);
    
    public Contact update(Contact contact, Long id);
    
    public void delete(Long id);
    
}
