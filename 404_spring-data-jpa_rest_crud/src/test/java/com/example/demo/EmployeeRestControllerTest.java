package com.example.demo;

import com.example.demo.entity.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class EmployeeRestControllerTest {

	private static MockHttpServletRequest req;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	private static final MediaType APPLICATION_JSON = MediaType.APPLICATION_JSON;

	@BeforeAll
	static void beforeAll() {
		req = new MockHttpServletRequest();
		req.setParameter("firstName", "John");
		req.setParameter("lastName", "Doe");
		req.setParameter("email", "john@test.com");
	}

	@Sql("/test_employee.sql")
	@Test
	void testFindAll() throws Exception {
		// MockReq
		MockHttpServletRequestBuilder builders =
				MockMvcRequestBuilders.get("/api/employees");

		MvcResult mvcResult = this.mockMvc.perform(builders)
					.andExpect(status().isOk())
					.andExpect(content().contentType(APPLICATION_JSON))
					.andExpect(jsonPath("$", hasSize(2)))
					.andReturn();

		MockHttpServletResponse res = mvcResult.getResponse();
		System.out.println(res.getContentAsString());
	}

	@Sql("/test_employee.sql")
	@Test
	void testGetEmployee() throws Exception {
		// MockReq
		MockHttpServletRequestBuilder builders =
				MockMvcRequestBuilders.get("/api/employees/11");

		MvcResult mvcResult = this.mockMvc.perform(builders)
											.andExpect(status().isOk())
											.andExpect(content().contentType(APPLICATION_JSON))
											.andReturn();
		MockHttpServletResponse res = mvcResult.getResponse();

		Employee created = this.mapper.readValue(res.getContentAsString(), Employee.class);

		assertEquals(created.getId(), 11);
		assertEquals(created.getFirstName(), "John");
		assertEquals(created.getLastName(), "Doe");
		assertEquals(created.getEmail(), "john@test.com");
	}

	@Test
	void testAddEmployee() throws Exception {
		Employee employee = new Employee("Steph", "Curry", "steph@test.com");

		// MockReq
		MockHttpServletRequestBuilder builders =
				MockMvcRequestBuilders.post("/api/employees")
										.contentType(APPLICATION_JSON)
										.content(this.mapper.writeValueAsString(employee));

		MvcResult mvcResult = this.mockMvc.perform(builders)
									.andExpect(status().isOk())
									.andReturn();
		MockHttpServletResponse res = mvcResult.getResponse();

		Employee created = this.mapper.readValue(res.getContentAsString(), Employee.class);
		System.out.println(created);

		assertEquals(employee.getFirstName(), created.getFirstName());
		assertEquals(employee.getLastName(), created.getLastName());
		assertEquals(employee.getEmail(), created.getEmail());
	}

}
