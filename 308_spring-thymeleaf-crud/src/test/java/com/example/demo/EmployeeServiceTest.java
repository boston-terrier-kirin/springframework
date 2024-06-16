package com.example.demo;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest
class EmployeeServiceTest {

	@Autowired
	JdbcTemplate jdbc;

	@Autowired
	EmployeeService employeeService;

	@BeforeEach
	void beforeEach() {
		jdbc.execute("insert into employee(first_name, last_name, email) values('John', 'Doe', 'john@test.com')");
		jdbc.execute("insert into employee(first_name, last_name, email) values('Jane', 'Doe', 'jane@test.com')");
	}

	@AfterEach
	void afterEach() {
		jdbc.execute("delete from employee");
	}

	@Sql("/test_employee.sql")
	@Test
	void testFindByIdEmployee() {
		Employee found = employeeService.findByEmail("john11@test.com");
		Employee employee = employeeService.findById(found.getId());

		assertEquals("John", employee.getFirstName());
		assertEquals("Doe", employee.getLastName());
		assertEquals("john11@test.com", employee.getEmail());
	}

	@Test
	void testNotFoundByIdEmployee() {
		assertThrows(RuntimeException.class, () -> {
			employeeService.findById(99);
		});
	}

	@Test
	void testNotFoundByEmailEmployee() {
		assertThrows(RuntimeException.class, () -> {
			employeeService.findByEmail("unknown@test.com");
		});
	}

	@Test
	void testFindAllEmployee() {
		List<Employee> all = employeeService.findAll();

		all.stream().forEach(System.out::println);

		assertEquals(2, all.size());
	}

	@Test
	void testCreateEmployee() {
		Employee employee = new Employee();
		employee.setFirstName("Steph");
		employee.setLastName("Smith");
		employee.setEmail("steph@test.com");

		Employee saved = employeeService.save(employee);

		assertEquals("Steph", saved.getFirstName());
		assertEquals("Smith", saved.getLastName());
		assertEquals("steph@test.com", saved.getEmail());
	}

	@Test
	void testUpdateEmployee() {
		Employee employee = employeeService.findByEmail("john@test.com");
		employee.setEmail("john.doe@test.com");

		Employee saved = employeeService.save(employee);
		assertEquals("john.doe@test.com", saved.getEmail());
	}

	@Test
	void testDeleteById() {
		Employee found = employeeService.findByEmail("john@test.com");
		int id = found.getId();
		employeeService.deleteById(id);

		assertThrows(RuntimeException.class, () -> {
			employeeService.findById(id);
		});
	}

}
