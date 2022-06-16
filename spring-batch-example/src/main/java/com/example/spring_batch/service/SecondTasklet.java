package com.example.spring_batch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

@Service
public class SecondTasklet implements Tasklet {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		logger.info("　--- 20秒スリープ");
		Thread.sleep(20000);
		return RepeatStatus.FINISHED;
	}

}
