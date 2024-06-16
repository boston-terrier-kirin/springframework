package com.example.demo;

import com.example.demo.models.CollegeStudent;
import com.example.demo.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

	private static int counter = 0;

	@Value("${info.app.name}")
	private String appName;

	@Value("${info.app.description}")
	private String appDescription;

	@Value("${info.app.version}")
	private String appVersion;

	@Value("${info.school.name}")
	private String appSchoolName;

	@Autowired
	ApplicationContext context;

	@Autowired
	CollegeStudent collegeStudent;

	@Autowired
	StudentGrades studentGrades;

	@BeforeEach
	public void beforeEach() {
		counter = counter + 1;
		System.out.println("Testing: " + appName + " - " + appDescription + " Version: " + appVersion + " - " + counter);

		collegeStudent.setFirstname("John");
		collegeStudent.setLastname("Doe");
		collegeStudent.setEmailAddress("john@test.com");

		studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 85.0, 76.5, 91.75)));
		collegeStudent.setStudentGrades(studentGrades);
	}

	@Test
	void test0001() {
		double grades = studentGrades.addGradeResultsForSingleClass(collegeStudent.getStudentGrades().getMathGradeResults());
		assertEquals(353.25, grades);
	}

	@Test
	void test0002() {
		double grades = studentGrades.addGradeResultsForSingleClass(collegeStudent.getStudentGrades().getMathGradeResults());
		assertNotEquals(0, grades);
	}

	@Test
	void test0003() {
		boolean result = studentGrades.isGradeGreater(90, 75);
		assertTrue(result);
	}

	@Test
	void test0004() {
		boolean result = studentGrades.isGradeGreater(75, 80);
		assertFalse(result);
	}

	@Test
	void test0005() {
		Object result = studentGrades.checkNull(collegeStudent.getStudentGrades().getMathGradeResults());
		assertNotNull(result);
	}

	@Test
	void test0006() {
		CollegeStudent student = context.getBean("collegeStudent", CollegeStudent.class);
		student.setFirstname("Jane");
		student.setLastname("Doe");
		student.setEmailAddress("jane@test.com");

		assertNotNull(student.getFirstname());
		assertNotNull(student.getLastname());
		assertNotNull(student.getEmailAddress());
	}

	@Test
	void test0007() {
		CollegeStudent student = context.getBean("collegeStudent", CollegeStudent.class);

		assertNotSame(collegeStudent, student);
	}

	@Test
	void test0008() {
		this.collegeStudent.setFirstname("Clay");
		ReflectionTestUtils.setField(this.collegeStudent, "id", "100");
		String firstNameAndId = ReflectionTestUtils.invokeMethod(this.collegeStudent, "getFirstNameAndId");

		assertEquals("Clay$100", firstNameAndId);
	}
}
