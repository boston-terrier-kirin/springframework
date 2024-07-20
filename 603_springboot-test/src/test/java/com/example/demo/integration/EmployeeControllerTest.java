package com.example.demo.integration;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    private void setUp() {
        this.employeeRepository.deleteAll();
    }

    @Test
    public void testCreateEmployee() throws Exception {
        Employee employee = Employee.builder()
                .firstName("kohei")
                .lastName("matsumoto")
                .email("kohei@test.com")
                .build();

        String json = this.objectMapper.writeValueAsString(employee);
        MockHttpServletRequestBuilder mockReq =
                MockMvcRequestBuilders.post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json);
        MvcResult mvcResult = this.mockMvc.perform(mockReq)
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isCreated())
                                .andReturn();

        String resJson = mvcResult.getResponse().getContentAsString();
        Employee result = this.objectMapper.readValue(resJson, Employee.class);

        assertEquals("kohei", result.getFirstName());
        assertEquals("matsumoto", result.getLastName());
        assertEquals("kohei@test.com", result.getEmail());
    }

    @Test
    public void testGetAllEmployee() throws Exception {
        Employee emp1 = new Employee(1L, "kohei", "matsumoto", "kohei@test.com");
        Employee emp2 = new Employee(2L, "kirin", "matsumoto", "kohei@test.com");
        Employee emp3 = new Employee(3L, "kuroro", "matsumoto", "kohei@test.com");
        List<Employee> empList = Arrays.asList(emp1, emp2, emp3);
        this.employeeRepository.saveAll(empList);

        MockHttpServletRequestBuilder mockReq =
                MockMvcRequestBuilders.get("/api/employees");
        MvcResult mvcResult = this.mockMvc.perform(mockReq)
                                    .andDo(MockMvcResultHandlers.print())
                                    .andExpect(MockMvcResultMatchers.status().isOk())
                                    .andReturn();

        String resJson = mvcResult.getResponse().getContentAsString();
        List<Employee> resultList = Arrays.asList(this.objectMapper.readValue(resJson, Employee[].class));

        assertEquals(3, resultList.size());
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        Employee emp = new Employee(1L, "kohei", "matsumoto", "kohei@test.com");
        Employee saved = this.employeeRepository.save(emp);

        MockHttpServletRequestBuilder mockReq =
                MockMvcRequestBuilders.get("/api/employees/" + saved.getId());
        MvcResult mvcResult = this.mockMvc.perform(mockReq)
                                            .andDo(MockMvcResultHandlers.print())
                                            .andExpect(MockMvcResultMatchers.status().isOk())
                                            .andReturn();

        String resJson = mvcResult.getResponse().getContentAsString();
        Employee result = this.objectMapper.readValue(resJson, Employee.class);

        assertEquals("kohei", result.getFirstName());
        assertEquals("matsumoto", result.getLastName());
        assertEquals("kohei@test.com", result.getEmail());
    }
}
