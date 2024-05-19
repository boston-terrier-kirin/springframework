package com.example.demo.controller;

import com.example.demo.entity.Test;
import com.example.demo.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    TestRepository testRepository;

    @GetMapping("/test1")
    public List<Test> test1() {
        Test test = createTest();
        testRepository.saveTest(test);

        System.out.println("💨💨💨💨💨 TEST1");

        List<Test> tests = testRepository.findAll();
        System.out.println("TEST1:" + tests);

        return tests;
    }

    @GetMapping("/test2")
    public List<Test> test2() {
        Test test = createTest();
        testRepository.saveTest(test);

        System.out.println("💨💨💨💨💨 TEST2");

        List<Test> tests = testRepository.findMsgsWithMessage("%test%");
        System.out.println("TEST2:" + tests);

        return tests;
    }

    @GetMapping("/test3")
    public Test test3() {
        System.out.println("💨💨💨💨💨 TEST3");

        Test test = testRepository.findById(1);
        System.out.println("TEST3:" + test);

        return test;
    }

    @GetMapping("/test4")
    public Map<String, Object> test4() {
        System.out.println("💨💨💨💨💨 TEST4");

        Map<String, Object> test = testRepository.findByMap(1);
        System.out.println("TEST4:" + test);

        return test;
    }

    @GetMapping("/test5")
    public List<Test> test5() {
        System.out.println("💨💨💨💨💨 TEST5");

        List<Test> tests = testRepository.findByNamedParameter(1);
        System.out.println("TEST5:" + tests);

        return tests;
    }

    @GetMapping("/test51")
    public Test test51() {
        System.out.println("💨💨💨💨💨 TEST51");

        Test test = createTest();
        testRepository.saveTest(test);

        return test;
    }

    @GetMapping("/test52")
    public int test52() {
        System.out.println("💨💨💨💨💨 TEST52");

        int result = testRepository.updateTest(1, "Updated Message", "Kuroro");
        System.out.println("TEST5:" + result);

        return result;
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