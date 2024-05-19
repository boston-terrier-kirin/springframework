package com.example.spring_jdbc.controller;

import com.example.spring_jdbc.entity.Test;
import com.example.spring_jdbc.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * https://speakerdeck.com/rshindo/jsug-2019-01
 * spring-boot-starter-data-jdbc
 *   と
 * spring-boot-starter-jdbc
 *   は違うらしい。
 */
@Controller
public class TestController {

    @Autowired
    TestRepository testRepository;

    @GetMapping("/test1")
    public String test1() {
        Test test = createTest();
        testRepository.saveTest(test);

        System.out.println("💨💨💨💨💨 TEST1");

        List<Test> tests = testRepository.findAll();
        System.out.println("TEST1:" + tests);

        return "test.html";
    }

    @GetMapping("/test2")
    public String test2() {
        Test test = createTest();
        testRepository.saveTest(test);

        System.out.println("💨💨💨💨💨 TEST2");

        List<Test> tests = testRepository.findMsgsWithMessage("%test%");
        System.out.println("TEST2:" + tests);

        return "test.html";
    }

    @GetMapping("/test3")
    public String test3() {
        {
            Test test = createTest();
            testRepository.saveTest(test);
        }

        System.out.println("💨💨💨💨💨 TEST3");

        Test test = testRepository.findById(1);
        System.out.println("TEST3:" + test);

        return "test.html";
    }

    @GetMapping("/test4")
    public String test4() {
        {
            Test test = createTest();
            testRepository.saveTest(test);
        }

        System.out.println("💨💨💨💨💨 TEST4");

        Map<String, Object> test = testRepository.findByMap(1);
        System.out.println("TEST4:" + test);

        return "test.html";
    }

    @GetMapping("/test5")
    public String test5() {
        {
            Test test1 = createTest();
            Test test2 = createTest();
            testRepository.saveTest(test1);
            testRepository.saveTest(test2);
        }

        System.out.println("💨💨💨💨💨 TEST5");

        List<Test> tests = testRepository.findByNamedParameter(1);
        System.out.println("TEST5:" + tests);

        return "test.html";
    }

    @GetMapping("/test51")
    public String test51() {
        System.out.println("💨💨💨💨💨 TEST51");

        Test test = createTest();
        testRepository.saveTest(test);

        return "test.html";
    }

    @GetMapping("/test52")
    public String test52() {
        System.out.println("💨💨💨💨💨 TEST52");

        int result = testRepository.updateTest(1, "Updated Message", "Kuroro");
        System.out.println("TEST5:" + result);

        return "test.html";
    }

    @GetMapping("/test101")
    public String test101() {
        System.out.println("💨💨💨💨💨 TEST101");

        Test test = createTest();
        System.out.println("TEST101.test: " + test);

        Test savedTest = testRepository.save(test);
        System.out.println("TEST101.savedTest: " + savedTest);

        // testとsavedTestは同じオブジェクトになってしまう。
        // それを回避するために、@Withを使うらしいが、詳細不明。
        if (test.equals(savedTest)) {
            System.out.println("Same");
        }

        return "test.html";
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
