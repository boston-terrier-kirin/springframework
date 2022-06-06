package com.example.spring_thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring_thymeleaf.dao.EmployeeRepository;
import com.example.spring_thymeleaf.entity.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> findAll() {
		return this.employeeRepository.findAllByOrderByLastNameAsc();
	}

	@Override
	public Employee findById(int theId) {
		Optional<Employee> result = this.employeeRepository.findById(theId);

		Employee employee = null;

		if (result.isPresent()) {
			employee = result.get();
		} else {
			throw new RuntimeException("Did not find employee id - " + theId);
		}

		return employee;
	}

	@Override
	public void save(Employee theEmployee) {
		this.employeeRepository.save(theEmployee);
	}

	@Override
	public void deleteById(int theId) {
		this.employeeRepository.deleteById(theId);
	}

}