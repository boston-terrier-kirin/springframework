package com.example.demo;

import com.example.demo.dao.AccountDao;
import com.example.demo.dao.MembershipDao;
import com.example.demo.model.Account;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AccountDao accountDao, MembershipDao membershipDao) {
		return runner -> {
			demoBeforeAdvice(accountDao, membershipDao);
		};
	}

	private void demoBeforeAdvice(AccountDao accountDao, MembershipDao membershipDao) {
		Account account = new Account();
		account.setName("John");
		account.setLevel("A");

		accountDao.addAccount(account, true);
		System.out.println("");

		accountDao.doWork();
		System.out.println("");

		System.out.println("$$$getter/setter");
		accountDao.setName("Jane");
		accountDao.getName();
		accountDao.setServiceCode("COUPON123");
		accountDao.getServiceCode();
		System.out.println("$$$getter/setter \n");

		membershipDao.addAccount();
	}

}
