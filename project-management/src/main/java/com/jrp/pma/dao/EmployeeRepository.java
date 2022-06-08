package com.jrp.pma.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jrp.pma.dto.EmployeeProject;
import com.jrp.pma.entity.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	@Override
	List<Employee> findAll();

	public static final String getEmployeeProjects_sql =
"""
select a.first_name as firstName, a.last_name as lastName, count(*) as projectCount
  from employee a
        inner join project_employee b
                on a.employee_id = b.employee_id
 group by a.first_name, a.last_name
""";
	
	@Query(nativeQuery = true, value = getEmployeeProjects_sql)
	public List<EmployeeProject> getEmployeeProjects();
}
