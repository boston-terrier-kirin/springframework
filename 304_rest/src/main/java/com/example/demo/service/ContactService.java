package com.example.demo.service;

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

    private int findIndexById(String id) {
        List<Contact> contacts = contactRepository.getContacts();

        return IntStream.range(0, contacts.size())
                .filter(index -> contacts.get(index).getId().equals(id))
                .findFirst()
                .orElseThrow();
    }
}
