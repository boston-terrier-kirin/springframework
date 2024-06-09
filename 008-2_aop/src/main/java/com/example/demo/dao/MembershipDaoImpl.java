package com.example.demo.dao;

import org.springframework.stereotype.Repository;

@Repository
public class MembershipDaoImpl implements MembershipDao {
    @Override
    public void addAccount() {
        System.out.println(getClass() + ": DO MY DB WORK -> ADDING AN MEMBER");
    }
}
