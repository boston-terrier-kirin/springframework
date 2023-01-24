package com.jrp.pma.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jrp.pma.dto.ChartData;
import com.jrp.pma.entity.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
	@Override
	public List<Project> findAll();
	
	public static final String getProjectStatus_sql = 
"""
select stage as label, count(*) as "value"
  from project
 group by stage
""";

	@Query(nativeQuery = true, value = getProjectStatus_sql)
	public List<ChartData> getProjectStatus();
}
