package com.example.demo.controller;

import com.example.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    ApplicationContext context;

    @GetMapping("/hello")
    public String hello() {
        DemoService demoA = context.getBean("DemoA", DemoService.class);
        DemoService demoB = context.getBean("DemoB", DemoService.class);

        System.out.println(demoB.greet());

        return demoA.greet();
    }
}
