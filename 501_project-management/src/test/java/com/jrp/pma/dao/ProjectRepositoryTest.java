package com.jrp.pma.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import com.jrp.pma.entity.Project;

// パッケージ名がcom.jrp.daoで、com.jrp.pma.dao と一致していない場合はこのアノテーションが必要になる。
// @ContextConfiguration(classes = ProjectManagementApplication.class)
// @DataJpaTest

@SpringBootTest
@RunWith(SpringRunner.class)
@SqlGroup({
			@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "classpath:schema.sql", "classpath:data.sql" }),
			@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = { "classpath:drop.sql" })
		})
public class ProjectRepositoryTest {

	@Autowired
	ProjectRepository projectRepository;

	@Test
	public void ifNewProjectSaved_thenSuccess() {
		Project project = new Project("昼ごはん", "COMPLETE", "お腹が空いた。");
		this.projectRepository.save(project);
		
		assertEquals(5, this.projectRepository.findAll().size());
	}
}
