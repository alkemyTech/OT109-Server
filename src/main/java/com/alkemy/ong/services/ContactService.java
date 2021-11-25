package com.alkemy.ong.services;

import com.alkemy.ong.entities.Contact;
import com.alkemy.ong.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;


@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> findAllContacts() {
        return contactRepository.findAll();
    }

    public Contact findContactById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("id %d no encontrado", id)));
    }

    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public Contact updateContact(Contact contact, Long id) {
        if (contactRepository.findById(id).isPresent()) {
            return contactRepository.save(contact);
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No se pudo Actualizar, id %d no encontrado", id));
    }

    public void deleteContactById(Long id) {
        if (contactRepository.findById(id).isPresent()) {
            contactRepository.deleteById(id);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("id %d no encontrado", id));
    }
}
