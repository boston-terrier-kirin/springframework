package com.example.demo;

import com.example.demo.game.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.demo")
public class AppGamingBasicExample {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppGamingBasicExample.class);

        {
            var gameRunner = context.getBean(GameRunner.class);
            System.out.println(gameRunner);
            gameRunner.run();
            System.out.println("-----");
        }

        {
            // GameRunnerはprototypeになっているので、hashcodeが変わる。
            var gameRunner = context.getBean(GameRunner.class);
            System.out.println(gameRunner);
            gameRunner.run();
        }

    }
}
