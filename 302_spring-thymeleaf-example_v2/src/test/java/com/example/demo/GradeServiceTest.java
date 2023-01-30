package com.example.demo;

import com.example.demo.model.Grade;
import com.example.demo.repository.GradeRepository;
import com.example.demo.service.GradeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class GradeServiceTest {

	@Mock
	private GradeRepository gradeRepository;

	@InjectMocks
	private GradeService gradeService;

	@Test
	public void getGradesTest() {
		when(gradeRepository.getGrades()).thenReturn(Arrays.asList(
				new Grade("Harry", "Portions", "C-"),
				new Grade("Hermione", "Arithmancy", "A+")
		));

		List<Grade> result = gradeService.getGrades();
		assertEquals("Harry", result.get(0).getName());
	}

	@Test
	public void getGradeIndexTest() {
		when(gradeRepository.getGrades()).thenReturn(Arrays.asList(
				new Grade("Harry", "Portions", "C-"),
				new Grade("Hermione", "Arithmancy", "A+")
		));

		List<Grade> result = gradeService.getGrades();
		var harry = result.get(0);
		var harmione = result.get(1);

		assertEquals(0, gradeService.getGradeIndex(harry.getId()));
		assertEquals(1, gradeService.getGradeIndex(harmione.getId()));
		assertEquals(Constants.NOT_FOUND, gradeService.getGradeIndex("DUMMY"));
	}

	@Test
	public void getGradeByIdTest() {
		Grade harry = new Grade("Harry", "Potions", "C-");
		Grade harmione = new Grade("Hermione", "Arithmancy", "A+");

		when(gradeRepository.getGrades()).thenReturn(Arrays.asList(harry, harmione));
		when(gradeRepository.getGrade(0)).thenReturn(harry);
		when(gradeRepository.getGrade(1)).thenReturn(harmione);

		assertEquals("Harry", gradeService.getGradeById(harry.getId()).getName());
		assertEquals("Hermione", gradeService.getGradeById(harmione.getId()).getName());
	}

	@Test
	public void updateGradeTest() {
		Grade harry = new Grade("Harry", "Potions", "C-");
		Grade harmione = new Grade("Hermione", "Arithmancy", "A+");

		when(gradeRepository.getGrades()).thenReturn(Arrays.asList(harry, harmione));

		// gradeRepository.updateGradeが何回呼ばれるか。
		gradeService.submitGrade(harry);
		verify(gradeRepository, times(1)).updateGrade(0, harry);

		gradeService.submitGrade(harmione);
		verify(gradeRepository, times(1)).updateGrade(1, harmione);
	}

	@Test
	public void addGradeTest() {
		Grade harry = new Grade("Harry", "Potions", "C-");

		when(gradeRepository.getGrades()).thenReturn(Arrays.asList(harry));

		// gradeRepository.updateGradeが何回呼ばれるか。
		Grade harmione = new Grade("Hermione", "Arithmancy", "A+");
		gradeService.submitGrade(harmione);
		verify(gradeRepository, times(1)).addGrade(harmione);
	}

}
