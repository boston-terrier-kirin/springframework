package com.example.demo;

import com.example.demo.service.BusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BusinessService businessService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("MAX: " + businessService.calculateMax());
		logger.info("-----");

		try {
			businessService.exception();
		} catch (Exception e) {
		}
	}
}
