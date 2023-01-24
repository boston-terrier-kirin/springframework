package com.jrp.pma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jrp.pma.entity.Employee;
import com.jrp.pma.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;

	@GetMapping
	public String displayEmployees(Model model) {
		List<Employee> employees = this.employeeService.getAll();
		model.addAttribute("employees", employees);

		return "/employees/list-employees";
	}

	@GetMapping("/new")
	public String displayForm(Model model) {
		model.addAttribute("employee", new Employee());
		return "/employees/new-employee";
	}

	@PostMapping("/save")
	public String createEmployee(Employee employee, Model model) {
		this.employeeService.save(employee);
		return "redirect:/employees/new";
	}
}
