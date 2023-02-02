package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service("DemoA")
public class DemoServiceA implements DemoService {
    @Override
    public String greet() {
        return "Demo A";
    }
}
