package com.example.spring_jdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot DevToolsのLiveReload設定
 * https://zenn.dev/adito/articles/3bc06d4822f28a
 */

@SpringBootApplication
public class SpringJdbcExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJdbcExampleApplication.class, args);
	}

}
