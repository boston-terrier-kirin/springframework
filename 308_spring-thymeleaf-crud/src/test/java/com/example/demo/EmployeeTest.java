package com.example.demo;

import com.example.demo.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmployeeTest {

    @Test
    void testNewEmployee() {
        Employee employee = new Employee("john", "doe", "john@test.com");
        assertEquals("john", employee.getFirstName());

        System.out.println(employee);
    }

    @Test
    void testSetId() {
        Employee employee = new Employee();
        employee.setId(100);

        assertEquals(100, employee.getId());
    }
}
