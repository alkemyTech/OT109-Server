package com.alkemy.ong.services.impl;

import com.alkemy.ong.dtos.responses.ContactListDTO;
import com.alkemy.ong.entities.Contact;
import com.alkemy.ong.repositories.ContactRepository;
import com.alkemy.ong.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Service
public class ContactServiceImpl implements ContactService{
    
    @Autowired
    private ContactRepository contactRepository;

    @Override
    public List<ContactListDTO> findAll() {
        List<Contact> contactList = contactRepository.findAll();
        List<ContactListDTO> contactDTOList = new ArrayList<>();
        for (Contact c : contactList) {
            contactDTOList.add(new ContactListDTO(c));
        }
        return contactDTOList;
    }

    @Override
    public Contact findById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("id %d no encontrado", id)));
    }

    @Override
    public Contact create(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact update(Contact contact, Long id) {
        if (contactRepository.findById(id).isPresent()) {
            return contactRepository.save(contact);
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No se pudo Actualizar, id %d no encontrado", id));
    }

    @Override
    public void delete(Long id) {
        if (contactRepository.findById(id).isPresent()) {
            contactRepository.deleteById(id);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("id %d no encontrado", id));
    }
}
