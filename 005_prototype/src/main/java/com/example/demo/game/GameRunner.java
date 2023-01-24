package com.example.demo.game;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value= ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GameRunner {
    GamingConsole game;

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
