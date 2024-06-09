package com.example.demo.service;

import com.example.demo.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EmployeeService {

    public List<Employee> findAll();

    public Employee findById(int id);

    public Employee save(Employee employee);

    public void deleteById(int id);
}
