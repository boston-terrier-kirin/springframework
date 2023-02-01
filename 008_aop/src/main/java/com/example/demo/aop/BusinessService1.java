package com.example.demo.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessService1 {
    @Autowired
    private DataService1 dataService;

    public Long calculateMax() {
        List<Integer> data = dataService.getData();
        return data.stream().count();
    }
}
