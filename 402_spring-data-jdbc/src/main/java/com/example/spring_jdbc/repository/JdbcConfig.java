package com.example.spring_jdbc.repository;

import com.example.spring_jdbc.entity.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback;
import org.springframework.data.relational.core.mapping.event.RelationalEvent;

// Configurationをつけているので、JdbcConfigはrepositoryパッケージに存在していないと、repositoryを自動的にcomponent-scanしてくれない。

@Configuration
@EnableJdbcRepositories
public class JdbcConfig extends AbstractJdbcConfiguration {
    @Bean
    public ApplicationListener<?> loggingListener() {

        return (ApplicationListener<ApplicationEvent>) event -> {
            if (event instanceof RelationalEvent) {
                System.out.println("Received an event: " + event);
            }
        };
    }

    @Bean
    public BeforeSaveCallback<Test> timeStampingSaveTime() {

        return (entity, aggregateChange) -> {
            System.out.println("★");
            return entity;
        };
    }
}
