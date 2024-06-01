package org.example.main;

import org.example.beans.Vehicle;
import org.example.config.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.function.Supplier;

public class Example {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(Config.class);

        Supplier<Vehicle> subaruSupplier = () -> {
            Vehicle veh = new Vehicle();
            veh.setName("Subaru");
            return veh;
        };

        // 条件によってBeanを作る場合に使える
        context.registerBean("subaru", Vehicle.class, subaruSupplier);

        var subaru = context.getBean("subaru");
        System.out.println(subaru);

        context.close();
    }
}
