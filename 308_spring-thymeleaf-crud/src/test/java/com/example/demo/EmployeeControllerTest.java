package com.example.demo;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class EmployeeControllerTest {

    // @BeforeAll method 'void com.example.demo.EmployeeControllerTest.beforeAll()' must be static
    private static MockHttpServletRequest req;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JdbcTemplate jdbc;

    @MockBean
    EmployeeService employeeService;

    @BeforeAll
    static void beforeAll() {
        req = new MockHttpServletRequest();
        req.setParameter("firstName", "John");
        req.setParameter("lastName", "Doe");
        req.setParameter("email", "john@test.com");
    }

    @BeforeEach
    void beforeEach() {
        // jdbc.execute("insert into employee(first_name, last_name, email) values('John', 'Doe', 'john@test.com')");
        // jdbc.execute("insert into employee(first_name, last_name, email) values('Jane', 'Doe', 'jane@test.com')");
    }

    @AfterEach
    void afterEach() {
        jdbc.execute("delete from employee");
    }

    @Test
    void testListEmployees() throws Exception {
        // employeeServiceのMock
        List<Employee> employeeList = Arrays.asList(new Employee("John", "Doe", "john@test.com"),
                                                    new Employee("Jane", "Doe", "jane@test.com"));
        when(employeeService.findAll()).thenReturn(employeeList);
        assertIterableEquals(employeeList, employeeService.findAll());

        // MockReq
        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.get("/employees/list");
        MvcResult mvcResult = mockMvc.perform(builders)
                                        .andExpect(status().isOk())
                                        .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        Map model = modelAndView.getModel();

        // Viewのテスト
        ModelAndViewAssert.assertViewName(modelAndView, "employees/list-employees");

        // Modelのテスト
        List<Employee> resultList = (List<Employee>) model.get("employees");
        assertIterableEquals(employeeList, resultList);
    }

    @Test
    void testShowFormForAdd() throws Exception {
        // MockReq
        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.get("/employees/showFormForAdd");
        MvcResult mvcResult = mockMvc.perform(builders)
                                        .andExpect(status().isOk())
                                        .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();

        // Viewのテスト
        ModelAndViewAssert.assertViewName(modelAndView, "employees/employee-form");
    }

    @Test
    void testShowFormForUpdate() throws Exception {
        // employeeServiceのMock
        Employee employee = new Employee("John", "Doe", "john@test.com");
        when(employeeService.findById(1)).thenReturn(employee);

        // MockReq
        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.get("/employees/showFormForUpdate?employeeId=1");
        MvcResult mvcResult = mockMvc.perform(builders)
                                     .andExpect(status().isOk())
                                     .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        Map model = modelAndView.getModel();

        // Viewのテスト
        ModelAndViewAssert.assertViewName(modelAndView, "employees/employee-form");

        // Modelのテスト
        Employee added = (Employee) model.get("employee");
        assertEquals(employee, added);
    }

    @Test
    void testSave() throws Exception {
        // MockReq
        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/employees/save")
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                    .param("firstName", req.getParameterValues("firstName"))
                                                    .param("lastName", req.getParameterValues("lastName"))
                                                    .param("email", req.getParameterValues("email"));
        MvcResult mvcResult = mockMvc.perform(builders)
                                        .andExpect(status().isFound())
                                        .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();

        // Viewのテスト
        ModelAndViewAssert.assertViewName(modelAndView, "redirect:/employees/list");

        // employeeService.saveがemployeeで呼ばれたことをverifyする
        Employee employee = new Employee("John", "Doe", "john@test.com");
        verify(employeeService).save(ArgumentMatchers.refEq(employee));
    }

    @Test
    void testDelete() throws Exception {
        // MockReq
        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.get("/employees/delete?employeeId=1");
        MvcResult mvcResult = mockMvc.perform(builders)
                                    .andExpect(status().isFound())
                                    .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();

        // Viewのテスト
        ModelAndViewAssert.assertViewName(modelAndView, "redirect:/employees/list");

        verify(employeeService).deleteById(1);
    }
}
