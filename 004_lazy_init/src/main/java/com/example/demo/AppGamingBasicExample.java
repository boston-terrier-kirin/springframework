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

        System.out.println("---- AnnotationConfigApplicationContext");

        // @Lazyを使えば、Constructor Injectionのタイミングを、実際に呼び出したタイミングに変えることができる。
        var gameRunner = context.getBean(GameRunner.class);
        gameRunner.run();

        var defs = context.getBeanDefinitionNames();
        for (var def : defs) {
            System.out.println(def);
        }
    }
}
