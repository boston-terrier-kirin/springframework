package com.example.demo.dao;

import com.example.demo.model.Account;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl implements AccountDao {

    private String name;
    private String serviceCode;

    @Override
    public void addAccount(Account account, boolean vipFlag) {
        System.out.println(getClass() + ": DO MY DB WORK -> ADDING AN ACCOUNT");
    }

    @Override
    public boolean doWork() {
        System.out.println(getClass() + ": DO SOME WORK");
        return false;
    }

    public String getName() {
        System.out.println(getClass() + ": getName");
        return name;
    }

    public void setName(String name) {
        System.out.println(getClass() + ": setName");
        this.name = name;
    }

    public String getServiceCode() {
        System.out.println(getClass() + ": getServiceCode");
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        System.out.println(getClass() + ": setServiceCode");
        this.serviceCode = serviceCode;
    }
}
