package com.example.demo.service;

import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.Contact;
import com.example.demo.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> getContacts() {
        return contactRepository.getContacts();
    }

    public Contact getContactById(String id) {
        var idx = findIndexById(id);
        return contactRepository.getContact(idx);
    }

    private int findIndexById(String id) {
        List<Contact> contacts = contactRepository.getContacts();

        return IntStream.range(0, contacts.size())
                .filter(index -> contacts.get(index).getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    public void saveContact(Contact contact) {
        contactRepository.saveContact(contact);
    }

    public void updateContact(String id, Contact contact) {
        var idx = findIndexById(id);
        contactRepository.updateContact(idx, contact);
    }

    public void deleteContact(String id) {
        var idx = findIndexById(id);
        contactRepository.deleteContact(idx);
    }
}
