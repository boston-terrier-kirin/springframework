package com.jrp.pma.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrp.pma.dao.ProjectRepository;
import com.jrp.pma.dto.ChartData;
import com.jrp.pma.entity.Project;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	public Project save(Project project) {
		return this.projectRepository.save(project);
	}

	public List<Project> getAll() {
		return this.projectRepository.findAll();
	}

	public List<ChartData> getProjectStatus() {
		return this.projectRepository.getProjectStatus();
	}
}
