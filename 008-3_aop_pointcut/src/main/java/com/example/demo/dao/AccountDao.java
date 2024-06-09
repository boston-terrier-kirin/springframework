package com.example.demo.dao;

import com.example.demo.model.Account;

public interface AccountDao {
    public void addAccount(Account account, boolean vipFlag);

    public boolean doWork();

    public String getName();

    public void setName(String name);

    public String getServiceCode();

    public void setServiceCode(String serviceCoce);
}
