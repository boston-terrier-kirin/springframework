package com.example.demo.game;

public class GameRunner {
    GamingConsole game;

    public GameRunner(GamingConsole game) {
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
