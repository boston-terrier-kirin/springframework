package com.example.demo.service;

import com.example.demo.exception.ContactNotFoundException;
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

    public Contact getContactById(String id) {
        return contactRepository.getContact(findIndexById(id));
    }

    public void saveContact(Contact contact) {
        contactRepository.saveContact(contact);
    }

    public List<Contact> getContacts() {
        return contactRepository.getContacts();
    }

    public void deleteContact(String id) {
        contactRepository.deleteContact(findIndexById(id));
    }

    private int findIndexById(String id) {
        return IntStream.range(0, contactRepository.getContacts().size())
                .filter(index -> contactRepository.getContacts().get(index).getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ContactNotFoundException(id));
    }
}
