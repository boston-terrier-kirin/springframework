package com.jrp.pma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrp.pma.dto.ChartData;
import com.jrp.pma.dto.EmployeeProject;
import com.jrp.pma.entity.Project;
import com.jrp.pma.service.EmployeeService;
import com.jrp.pma.service.ProjectService;

@Controller
public class HomeController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/")
	public String displayHome(Model model) throws JsonProcessingException {
		List<Project> projects = this.projectService.getAll();
		List<ChartData> projectStatus = this.projectService.getProjectStatus();
		List<EmployeeProject> employeeProjects = this.employeeService.getEmployeeProjects();

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(projectStatus);

		model.addAttribute("projects", projects);
		model.addAttribute("projectStatus", jsonString);
		model.addAttribute("employeeProjects", employeeProjects);

		return "/main/home";
	}
}
