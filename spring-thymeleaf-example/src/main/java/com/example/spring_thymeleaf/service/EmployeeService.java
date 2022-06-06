package com.example.spring_thymeleaf.service;

import java.util.List;

import com.example.spring_thymeleaf.entity.Employee;

public interface EmployeeService {

	public List<Employee> findAll();
	
	public Employee findById(int theId);
	
	public void save(Employee theEmployee);
	
	public void deleteById(int theId);
	
}
