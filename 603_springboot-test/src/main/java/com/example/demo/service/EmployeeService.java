package com.example.demo.service;

import com.example.demo.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    public Employee saveEmployee(Employee employee);

    public List<Employee> getAllEmployee();

    public Optional<Employee> getEmployeeById(Long id);

    public Employee updateEmployee(Employee employee);

    public void deleteEmployee(Long id);
}
