package com.example.demo;

import com.example.demo.game.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppGamingBasicExample {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppGamingConfig.class);

        var gameRunner = context.getBean(GameRunner.class);
        gameRunner.run();
    }
}
