package com.example.demo;

import com.example.demo.game.GameRunner;
import com.example.demo.game.GamingConsole;
import com.example.demo.game.MarioGame;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppGamingConfig {

    @Bean
    public GamingConsole mario() {
        return new MarioGame();
    }

    // @BeanでもAutowireしてくれる。
    @Bean
    public GameRunner gameRunner(GamingConsole game) {
        return new GameRunner(game);
    }
}
