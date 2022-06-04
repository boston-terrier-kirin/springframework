package com.example.spring_jdbc.repository;

import com.example.spring_jdbc.entity.Test;
import org.springframework.data.repository.CrudRepository;

public interface Test2Repository extends CrudRepository<Test, Integer> {
}
