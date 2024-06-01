package org.example.main;

import org.example.beans.Vehicle;
import org.example.config.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Example {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(Config.class);

        Vehicle veh = context.getBean(Vehicle.class);
        System.out.println(veh);

        context.close();
    }
}
