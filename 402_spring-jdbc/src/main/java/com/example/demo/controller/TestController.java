package com.example.demo.controller;

import com.example.demo.entity.Test;
import com.example.demo.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    TestRepository testRepository;

    @GetMapping("/test1")
    public List<Test> test1() {
        Test test = createTest();
        this.testRepository.saveTest(test);

        var tests = this.testRepository.findAll();
        System.out.println(tests);

        return tests;
    }

    Test createTest() {
        Test test = new Test();
        test.setName("test");
        test.setMessage("This is a test");
        test.setCreatedBy("Kirin");
        test.setCreatedAt(LocalDateTime.now());

        return test;
    }
}