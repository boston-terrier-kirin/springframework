package com.example.demo.repository;

import com.example.demo.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testSaveEmployee() {
        Employee employee = Employee.builder()
                                    .firstName("kohei")
                                    .lastName("matsumoto")
                                    .email("kohei@test.com")
                                    .build();

        Employee saved = this.employeeRepository.save(employee);
        System.out.println(saved);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetAllEmployees() {
        Employee john = Employee.builder()
                                .firstName("john")
                                .lastName("eoe")
                                .email("john@test.com")
                                .build();
        Employee jane = Employee.builder()
                                .firstName("jane")
                                .lastName("eoe")
                                .email("john@test.com")
                                .build();
        this.employeeRepository.save(john);
        this.employeeRepository.save(jane);

        List<Employee> employeeList = this.employeeRepository.findAll();

        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @Test
    public void testGetEmployeeById() {
        Employee employee = Employee.builder()
                                    .firstName("kohei")
                                    .lastName("matsumoto")
                                    .email("kohei@test.com")
                                    .build();
        this.employeeRepository.save(employee);

        Employee found = this.employeeRepository.findById(employee.getId()).get();

        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("kohei@test.com");
    }

    @Test
    public void testGetEmployeeByEmail() {
        Employee employee = Employee.builder()
                                    .firstName("kohei")
                                    .lastName("matsumoto")
                                    .email("kohei@test.com")
                                    .build();
        this.employeeRepository.save(employee);

        Employee found = this.employeeRepository.findByEmail("kohei@test.com").get();

        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("kohei@test.com");
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = Employee.builder()
                                    .firstName("kohei")
                                    .lastName("matsumoto")
                                    .email("kohei@test.com")
                                    .build();
        this.employeeRepository.save(employee);

        Employee found = this.employeeRepository.findByEmail("kohei@test.com").get();
        found.setEmail("kohei.matsumoto@test.com");
        this.employeeRepository.save(found);

        Employee updated = this.employeeRepository.findByEmail("kohei.matsumoto@test.com").get();

        assertThat(updated).isNotNull();
        assertThat(updated.getEmail()).isEqualTo("kohei.matsumoto@test.com");
    }

    @Test
    public void testDeleteEmployee() {
        Employee employee = Employee.builder()
                                    .firstName("kohei")
                                    .lastName("matsumoto")
                                    .email("kohei@test.com")
                                    .build();
        this.employeeRepository.save(employee);

        Employee found = this.employeeRepository.findByEmail("kohei@test.com").get();
        this.employeeRepository.delete(found);

        Optional<Employee> deleted = this.employeeRepository.findByEmail("kohei@test.com");

        assertThat(deleted).isEmpty();
    }

    @Test
    public void testFindByFullName() {
        Employee employee = Employee.builder()
                                    .firstName("kohei")
                                    .lastName("matsumoto")
                                    .email("kohei@test.com")
                                    .build();
        this.employeeRepository.save(employee);

        Employee found = this.employeeRepository.findByFullName("kohei", "matsumoto");

        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo("kohei");
        assertThat(found.getLastName()).isEqualTo("matsumoto");
    }

    @Test
    public void testFindByFamilyName() {
        Employee employee = Employee.builder()
                                    .firstName("kohei")
                                    .lastName("matsumoto")
                                    .email("kohei@test.com")
                                    .build();
        this.employeeRepository.save(employee);

        Employee found = this.employeeRepository.findByFamilyName("matsumoto");

        assertThat(found).isNotNull();
        assertThat(found.getLastName()).isEqualTo("matsumoto");
    }

    @Test
    public void testFindByEmailAndFullName() {
        Employee employee = Employee.builder()
                                    .firstName("kohei")
                                    .lastName("matsumoto")
                                    .email("kohei@test.com")
                                    .build();
        this.employeeRepository.save(employee);

        Employee found = this.employeeRepository.findByEmailAndFullName("kohei@test.com", "kohei", "matsumoto");

        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo("kohei");
        assertThat(found.getLastName()).isEqualTo("matsumoto");
    }
}
