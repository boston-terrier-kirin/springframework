package com.example.demo;

import com.example.demo.dao.ApplicationDao;
import com.example.demo.models.CollegeStudent;
import com.example.demo.models.StudentGrades;
import com.example.demo.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	ApplicationContext context;

	@Autowired
	CollegeStudent studentOne;

	@Autowired
	StudentGrades studentGrades;

	@MockBean
	ApplicationDao dao;

	@Autowired
	ApplicationService service;

	@BeforeEach
	void beforeEach() {
		studentOne.setFirstname("John");
		studentOne.setLastname("Doe");
		studentOne.setEmailAddress("john@test.com");
		studentOne.setStudentGrades(studentGrades);
	}

	@Test
	void test0001() {
		List<Double> grades = Arrays.asList(10.0, 15.0, 5.0);

		// setUp -> execute -> assert -> verify
		when(dao.addGradeResultsForSingleClass(grades)).thenReturn(30.0);

		assertEquals(30.0, service.addGradeResultsForSingleClass(grades));

		verify(dao).addGradeResultsForSingleClass(grades);
		verify(dao, times(1)).addGradeResultsForSingleClass(grades);
	}

	@Test
	void test0002() {
		List<Double> grades = Arrays.asList(10.0, 15.0, 5.0);
		when(dao.addGradeResultsForSingleClass(grades)).thenThrow(RuntimeException.class);

		assertThrows(RuntimeException.class, () -> service.addGradeResultsForSingleClass(grades));
	}
}
