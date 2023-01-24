package com.example.demo;

import com.example.demo.hello.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HelloWorldExample {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(HelloWorldConfig.class);

        // 数を取得
        System.out.println(context.getBeanDefinitionCount());
        System.out.println("-----");

        // 一覧取得
        var defs = context.getBeanDefinitionNames();
        for (String def : defs) {
            System.out.println(def);
        }
        System.out.println("-----");

        // StringもBeanになれる。
        System.out.println(context.getBean("name"));
        System.out.println(context.getBean("age"));
        System.out.println("-----");

        {
            // personを指定すれば、そっちが優先される。
            Person person = (Person) context.getBean("person");
            System.out.println(person.name());
            System.out.println(person.age());
            System.out.println("-----");
        }

        {
            // Personが複数ある場合、@Primaryが優先される。
            Person person = context.getBean(Person.class);
            System.out.println("Person.class: " + person);
            System.out.println("-----");
        }

        {
            // personAutowiredを指定すれば、そっちが優先される。
            var person = context.getBean("personAutowired");
            System.out.println("personAutowired: " + person);
            System.out.println("-----");
        }

        {
            var person = context.getBean("personAutowired2");
            System.out.println("personAutowired2: " + person);
            System.out.println("-----");
        }

    }
}
