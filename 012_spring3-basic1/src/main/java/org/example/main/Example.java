package org.example.main;

import org.example.beans.Vehicle;
import org.example.config.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Example {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(Config.class);

        // メソッド名で指定できる。
        {
            Vehicle veh = context.getBean("vehicle1", Vehicle.class);
            System.out.println(veh);
        }

        // メソッド名で指定できる。
        {
            Vehicle veh = context.getBean("vehicle2", Vehicle.class);
            System.out.println(veh);
        }

        // @Primary
        {
            Vehicle veh = context.getBean(Vehicle.class);
            System.out.println(veh);
        }

        String stringValue = context.getBean(String.class);
        Integer integerValue = context.getBean(Integer.class);

        System.out.println(stringValue);
        System.out.println(integerValue);
    }
}
