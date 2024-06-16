package com.example.demo;

import com.example.demo.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ObjectMapperTest {

	@Test
	void testWriteValueAsString() throws JsonProcessingException {
		Employee employee = new Employee();
		employee.setFirstName("John");
		employee.setLastName("Doe");
		employee.setEmail("john.doe@test.com");

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(employee);

		System.out.println(json);
	}

	@Test
	void testReadValue() throws JsonProcessingException {
		String json = "[{\"id\":11,\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john@test.com\"},{\"id\":22,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane@test.com\"}]";

		ObjectMapper mapper = new ObjectMapper();
		List<Employee> employeeList = mapper.readValue(json, new TypeReference<List<Employee>>() {});
		System.out.println(employeeList);
	}

}
