package com.example.spring_batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.example.spring_batch.service.BatchExecutorTask;

@Configuration
public class BatchExecutorConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private BatchExecutorTask batchExecutorTask;

	@Bean(name = "batchJobLauncher")
	public JobLauncher jobLauncher(JobRepository jobRepository) {
		SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
		simpleAsyncTaskExecutor.setConcurrencyLimit(1);

		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(1);
		threadPoolTaskExecutor.initialize();

		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		jobLauncher.setTaskExecutor(threadPoolTaskExecutor);

		return jobLauncher;
	}
	
	@Bean(name = "batchExecutorJob")
	public Job batchExecutorJob() {
		return this.jobBuilderFactory.get("Batch Executor Job")
										.incrementer(new RunIdIncrementer())
										.start(executeBatch())
										.build();
	}

	private Step executeBatch() {
		return this.stepBuilderFactory.get("Execute Batch")
										.tasklet(this.batchExecutorTask)
										.build();
	}
}
