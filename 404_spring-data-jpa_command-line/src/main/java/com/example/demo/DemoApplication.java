package com.example.demo;

import com.example.demo.dao.StudentDao;
import com.example.demo.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(StudentDao studentDao) {
		// void run(String... args) をlambdaで実装している
		return runner -> {
			createStudent(studentDao);
		};
	}

	private void createStudent(StudentDao studentDao) {
		Student student = new Student("Stave", "Doe", "doe@gmail.com");
		studentDao.save(student);

		System.out.println(student);
	}

	private void findStudent(StudentDao studentDao) {
		Student student = studentDao.findById(1);
		System.out.println(student);
	}

	private void findStudents(StudentDao studentDao) {
		List<Student> students = studentDao.findAll();
		System.out.println(students);
	}

	private void queryForStudentByLastName(StudentDao studentDao) {
		List<Student> students = studentDao.findByLastName("Doe");
		System.out.println(students);
	}

	private void updateStudent(StudentDao studentDao) {
		Student student = studentDao.findById(1);
		student.setFirstName("Steph");
		student.setLastName("Curry");

		studentDao.update(student);
	}

	private void deleteStudent(StudentDao studentDao) {
		studentDao.delete(1);
	}

	private void deleteAllStudents(StudentDao studentDao) {
		studentDao.deleteAll();
	}

}
