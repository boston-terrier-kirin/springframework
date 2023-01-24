package com.example.demo.repository;

import com.example.demo.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int saveTest(Test test){
        String sql = "INSERT INTO TEST (NAME, MESSAGE, CREATED_AT, CREATED_BY) VALUES (?,?,?,?)";

        return jdbcTemplate.update(sql,
                test.getName(), test.getMessage(), test.getCreatedAt(), test.getCreatedBy());
    }

    public List<Test> findAll() {
        return jdbcTemplate.query("select * from TEST", new BeanPropertyRowMapper(Test.class));
    }
}
