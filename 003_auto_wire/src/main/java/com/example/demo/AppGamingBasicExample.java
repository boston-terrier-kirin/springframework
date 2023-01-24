package com.example.demo;

import com.example.demo.game.*;
import com.example.demo.park.DogRun;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.demo")
public class AppGamingBasicExample {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppGamingBasicExample.class);

        var gameRunner = context.getBean(GameRunner.class);
        gameRunner.run();

        var dogRun = context.getBean(DogRun.class);
        dogRun.run();
    }
}
