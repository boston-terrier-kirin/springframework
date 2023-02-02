package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service("DemoB")
public class DemoServiceB implements DemoService {
    @Override
    public String greet() {
        return "Demo B";
    }
}
