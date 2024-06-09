package com.example.demo.dao;

import com.example.demo.model.Account;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl implements AccountDao {
    @Override
    public void addAccount(Account account, boolean vipFlag) {
        System.out.println(getClass() + ": DO MY DB WORK -> ADDING AN ACCOUNT");
    }

    @Override
    public boolean doWork() {
        System.out.println(getClass() + ": DO SOME WORK");
        return false;
    }
}
