package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EmployeeServiceTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        this.employee = Employee.builder()
                .id(1L)
                .firstName("kohei")
                .lastName("matsumoto")
                .email("kohei@test.com")
                .build();
    }

    @Test
    public void testSaveEmployee() {
        given(this.employeeRepository.findByEmail(this.employee.getEmail()))
                .willReturn(Optional.empty());
        given(this.employeeRepository.save(this.employee))
                .willReturn(this.employee);

        Employee saved = this.employeeService.saveEmployee(this.employee);

        assertThat(saved).isSameAs(this.employee);
    }

    @Test
    public void testSaveEmployeeWithEmailAlreadyExists() {
        given(this.employeeRepository.findByEmail(this.employee.getEmail()))
                .willReturn(Optional.of(this.employee));

        Assertions.assertThrows(ResourceNotFoundException.class,
                                    ()-> this.employeeService.saveEmployee(this.employee));
    }

    @Test
    public void testGetAllEmployee() {
        Employee emp1 = new Employee(1L, "kirin", "matsumoto", "kirin@test.com");
        Employee emp2 = new Employee(2L, "kuroro", "matsumoto", "kirin@test.com");

        given(this.employeeRepository.findAll())
                .willReturn(Arrays.asList(emp1, emp2));

        List<Employee> allEmpList = this.employeeService.getAllEmployee();

        assertIterableEquals(Arrays.asList(emp1, emp2), allEmpList);
    }

    @Test
    public void testGetEmployeeById() {
        given(this.employeeRepository.findById(1L))
                .willReturn(Optional.of(this.employee));

        {
            Optional<Employee> found = this.employeeService.getEmployeeById(1L);
            assertEquals(this.employee, found.get());
        }

        {
            Optional<Employee> found = this.employeeService.getEmployeeById(2L);
            assertEquals(Optional.empty(), found);
        }
    }

    @Test
    public void testUpdateEmployee() {
        given(this.employeeRepository.save(this.employee))
                .willReturn(this.employee);

        Employee saved = this.employeeService.updateEmployee(employee);

        assertEquals(this.employee, saved);
    }

    @Test
    public void testDeleteEmployee() {
        willDoNothing().given(this.employeeRepository).deleteById(1L);

        this.employeeService.deleteEmployee(1L);

        verify(this.employeeRepository, times(1)).deleteById(1L);
    }
}
