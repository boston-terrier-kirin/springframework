package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
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

@AutoConfigureMockMvc
@SpringBootTest
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateEmployee() throws Exception {
        Employee employee = Employee.builder()
                .firstName("kohei")
                .lastName("matsumoto")
                .email("kohei@test.com")
                .build();

        given(this.employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                .willReturn(employee);

        String json = this.objectMapper.writeValueAsString(employee);
        MockHttpServletRequestBuilder mockReq =
                MockMvcRequestBuilders.post("/api/employees")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(json);
        ResultActions res = this.mockMvc.perform(mockReq);

        res.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }

    @Test
    public void testGetAllEmployee1() throws Exception {
        Employee emp1 = new Employee(1L, "kohei", "matsumoto", "kohei@test.com");
        Employee emp2 = new Employee(2L, "kirin", "matsumoto", "kohei@test.com");
        Employee emp3 = new Employee(3L, "kuroro", "matsumoto", "kohei@test.com");
        List<Employee> empList = Arrays.asList(emp1, emp2, emp3);

        given(this.employeeService.getAllEmployee())
                .willReturn(empList);

        MockHttpServletRequestBuilder mockReq =
                MockMvcRequestBuilders.get("/api/employees");
        MvcResult mvcResult = this.mockMvc.perform(mockReq)
                                            .andExpect(MockMvcResultMatchers.status().isOk())
                                            .andReturn();

        String resJson = mvcResult.getResponse().getContentAsString();
        List<Employee> resultList = Arrays.asList(this.objectMapper.readValue(resJson, Employee[].class));

        assertIterableEquals(empList, resultList);
    }

    @Test
    public void testGetAllEmployee2() throws Exception {
        Employee emp1 = new Employee(1L, "kohei", "matsumoto", "kohei@test.com");
        Employee emp2 = new Employee(2L, "kirin", "matsumoto", "kohei@test.com");
        Employee emp3 = new Employee(3L, "kuroro", "matsumoto", "kohei@test.com");
        List<Employee> empList = Arrays.asList(emp1, emp2, emp3);

        given(this.employeeService.getAllEmployee())
                .willReturn(empList);

        MockHttpServletRequestBuilder mockReq =
                MockMvcRequestBuilders.get("/api/employees");
        ResultActions res = this.mockMvc.perform(mockReq);

        res.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(3)));
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        Employee emp1 = new Employee(1L, "kohei", "matsumoto", "kohei@test.com");

        given(this.employeeService.getEmployeeById(1L))
                .willReturn(Optional.of(emp1));

        MockHttpServletRequestBuilder mockReq =
                MockMvcRequestBuilders.get("/api/employees/1");
        MvcResult mvcResult = this.mockMvc.perform(mockReq)
                                            .andDo(MockMvcResultHandlers.print())
                                            .andExpect(MockMvcResultMatchers.status().isOk())
                                            .andReturn();

        String resJson = mvcResult.getResponse().getContentAsString();
        Employee result = this.objectMapper.readValue(resJson, Employee.class);

        assertEquals(emp1, result);
    }

    @Test
    public void testGetEmployeeById_notFound() throws Exception {
        Employee emp1 = new Employee(1L, "kohei", "matsumoto", "kohei@test.com");

        given(this.employeeService.getEmployeeById(1L))
                .willReturn(Optional.of(emp1));

        MockHttpServletRequestBuilder mockReq =
                MockMvcRequestBuilders.get("/api/employees/2");
        MvcResult mvcResult = this.mockMvc.perform(mockReq)
                                            .andDo(MockMvcResultHandlers.print())
                                            .andExpect(MockMvcResultMatchers.status().isNotFound())
                                            .andReturn();

        String resJson = mvcResult.getResponse().getContentAsString();
        System.out.println("response:" + resJson);
    }

    @Test
    public void testUpdateEmployeeById() throws Exception {
        Employee emp1 = new Employee(1L, "kohei", "matsumoto", "kohei@test.com");
        given(this.employeeService.updateEmployee(emp1))
                .willReturn(emp1);

        String json = this.objectMapper.writeValueAsString(emp1);
        MockHttpServletRequestBuilder mockReq =
                MockMvcRequestBuilders.post("/api/employees/1")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(json);
        MvcResult mvcResult = this.mockMvc.perform(mockReq)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String resJson = mvcResult.getResponse().getContentAsString();
        Employee result = this.objectMapper.readValue(resJson, Employee.class);

        assertEquals(emp1, result);
    }
}
