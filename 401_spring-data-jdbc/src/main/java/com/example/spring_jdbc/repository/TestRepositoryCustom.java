package com.example.spring_jdbc.repository;

import com.example.spring_jdbc.entity.Test;

import java.util.List;
import java.util.Map;

// https://speakerdeck.com/rshindo/jsug-2019-01?slide=23
// TestRepositoryでCrudRepositoryの機能が使えるので、saveとかは不要。
// Spring Data JDBCの仕組みとしては、カスタムなSQLをJdbcTemplateで書くように思える。
public interface TestRepositoryCustom {
    public List<Test> findAll();

    public List<Test> findMsgsWithMessage(String message);

    public Test findById(int id);

    public Map<String, Object> findByMap(int id);

    public List<Test> findByNamedParameter(int id);

    public int saveTest(Test test);

    public int updateTest(int id, String message, String updatedBy);
}
