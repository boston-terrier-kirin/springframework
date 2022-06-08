package com.jrp.pma.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrp.pma.dao.EmployeeRepository;
import com.jrp.pma.dto.EmployeeProject;
import com.jrp.pma.entity.Employee;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	public Employee save(Employee employee) {
		return this.employeeRepository.save(employee);
	}

	public List<Employee> getAll() {
		return this.employeeRepository.findAll();
	}

	public List<EmployeeProject> getEmployeeProjects() {
		return this.employeeRepository.getEmployeeProjects();
	}
}
