package com.example.spring_thymeleaf.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.spring_thymeleaf.entity.Employee;
import com.example.spring_thymeleaf.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/list")
	public String listEmployee(Model model) {
		List<Employee> employees = this.employeeService.findAll();
		model.addAttribute("employees", employees);
		return "/employees/list-employees";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);

		return "/employees/employee-form";
	}

	@PostMapping("/save")
	public String save(Employee employee) {
		logger.info(employee.toString());
		
		this.employeeService.save(employee);
		
		return "redirect:/employees/list";
	}

	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable("id") int id, Model model) {
		Employee employee = this.employeeService.findById(id);
		model.addAttribute("employee", employee);

		return "/employees/employee-form";
	}

	/**
	 * ここはDeleteMappingにすべき？
	 */
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id, Model model) {
		this.employeeService.deleteById(id);
		
		return "redirect:/employees/list";
	}
}
