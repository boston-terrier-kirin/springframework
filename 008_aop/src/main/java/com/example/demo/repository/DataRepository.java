package com.example.demo.repository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataRepository {

    public List<Integer> getData() {
        return List.of(
                30,
                11,
                23
        );
    }
}
