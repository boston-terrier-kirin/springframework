package com.example.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.entity.Course;


public interface CourseRepository extends CrudRepository<Course, Long> {

}