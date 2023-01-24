package com.example.spring_jdbc.repository;

import com.example.spring_jdbc.entity.Test;
import org.springframework.data.repository.CrudRepository;

// https://speakerdeck.com/rshindo/jsug-2019-01?slide=23
// CrudRepositoryとTestRepositoryCustomの両方が使えるようになる。
public interface TestRepository extends CrudRepository<Test, Integer>, TestRepositoryCustom {
}
