package com.example.demo.park;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DogRun {

    // Fieldインジェクションの場合、@Autowiredが必要になる。
    @Autowired
    private FrenchBulldog kuroro;

    public void run() {
        System.out.println(this.kuroro);
    }
}
