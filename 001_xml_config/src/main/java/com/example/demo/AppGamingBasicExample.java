package com.example.demo;

import com.example.demo.game.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppGamingBasicExample {
    public static void main(String[] args) {
        var context = new ClassPathXmlApplicationContext("applicationContext.xml");

        context.getBean(GameRunner.class).run();

    }
}
