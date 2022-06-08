package com.jrp.pma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jrp.pma.entity.Employee;
import com.jrp.pma.entity.Project;
import com.jrp.pma.service.EmployeeService;
import com.jrp.pma.service.ProjectService;

@Controller
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	ProjectService projectService;

	@Autowired
	EmployeeService employeeService;

	@GetMapping
	public String displayProjects(Model model) {
		List<Project> projects = this.projectService.getAll();
		model.addAttribute("projects", projects);

		return "/projects/list-projects";
	}

	@GetMapping("/new")
	public String displayForm(Model model) {
		List<Employee> employees = this.employeeService.getAll();
		model.addAttribute("allEmployees", employees);
		model.addAttribute("project", new Project());

		return "/projects/new-project";
	}

	@PostMapping("/save")
	public String createProject(Project project, Model model) {
		this.projectService.save(project);
		return "redirect:/projects";
	}
}
