package com.example.demo.rowmapper;

import com.example.demo.entity.Test;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestRowMapper implements RowMapper<Test> {

    @Override
    public Test mapRow(ResultSet rs, int rowNum) throws SQLException {
        Test test = new Test();
        test.setId(rs.getInt("ID"));
        test.setName(rs.getString("NAME"));
        test.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
        test.setCreatedBy(rs.getString("CREATED_BY"));

        if(null!=rs.getTimestamp("UPDATED_AT")){
            test.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
        }
        test.setUpdatedBy(rs.getString("UPDATED_BY"));

        return test;
    }
}
