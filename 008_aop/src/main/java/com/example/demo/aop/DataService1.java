package com.example.demo.aop;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService1 {

    public List<Integer> getData() {
        return List.of(
                30,
                11,
                23
        );
    }
}
