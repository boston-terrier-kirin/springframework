package com.example.demo.dao;

import com.example.demo.entity.Student;

import java.util.List;

public interface StudentDao {
    public void save(Student student);

    public Student findById(Integer id);

    public List<Student> findAll();

    public List<Student> findByLastName(String lastName);

    public void update(Student student);

    public void delete(Integer id);

    public int deleteAll();
}
