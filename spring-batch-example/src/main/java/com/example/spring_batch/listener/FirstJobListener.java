package com.example.spring_batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstJobListener implements JobExecutionListener {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.info("■START: " + jobExecution.getJobInstance().getJobName());
		logger.info("　processId: " + jobExecution.getJobParameters().getLong("processId"));
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.info("■END: " + jobExecution.getJobInstance().getJobName());
		logger.info("　processId: " + jobExecution.getJobParameters().getLong("processId"));
	}
}
