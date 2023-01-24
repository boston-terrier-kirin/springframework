package com.example.demo.game;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class GameRunner {
    GamingConsole game;

    @PostConstruct
    public void afterInit() {
        System.out.println("AFTER_INIT");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("PRE_DESTROY");
    }

    public GameRunner(GamingConsole game) {
        System.out.println("INIT!!");
        this.game = game;
    }

    public void run() {
        System.out.println("Running game: " + game);

        this.game.up();
        this.game.down();
        this.game.left();
        this.game.right();
    }
}
