package com.example.demo.service;

import com.example.demo.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessService {
    @Autowired
    private DataRepository dataRepository;

    public Long calculateMax() {
        List<Integer> data = dataRepository.getData();
        return data.stream().count();
    }

    public void exception() {
        throw new RuntimeException("Something Went Wrong");
    }
}
