package com.example.demo.controller;

import com.example.demo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

}