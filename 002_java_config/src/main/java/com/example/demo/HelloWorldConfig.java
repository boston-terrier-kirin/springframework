package com.example.demo;

import com.example.demo.hello.Person;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class HelloWorldConfig {
    @Bean
    public String name() {
        return "Kirin";
    }

    @Bean
    @Qualifier("Kuroro")
    public String dog() {
        return "Kuroro";
    }

    @Bean("age")
    public int myAge() {
        return 14;
    }

    @Bean
    @Primary
    public Person person() {
        return new Person("Kuroro", 2);
    }

    @Bean
    public Person personAutowired(String name, int age) {
        return new Person(name, age);
    }

    @Bean
    public Person personAutowired2(@Qualifier("Kuroro") String myDog, int age) {
        return new Person(myDog, age);
    }
}
