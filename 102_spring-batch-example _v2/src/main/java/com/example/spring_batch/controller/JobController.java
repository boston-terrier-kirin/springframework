package com.example.spring_batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_batch.model.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api/job")
public class JobController {

	@Autowired
	private org.springframework.context.ApplicationContext context;

	@GetMapping("/start/{jobName}")
	public Response startJob(@PathVariable String jobName) throws JobExecutionAlreadyRunningException,
			JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {

		JobLauncher jobLauncher = context.getBean("batchJobLauncher", JobLauncher.class);
		Job job = context.getBean("batchExecutorJob", Job.class);

		// とりあえず採番した感じで
		long processId = System.currentTimeMillis();

		JobParameters params = new JobParametersBuilder()
									.addString("jobName", jobName)
									.addLong("processId", processId)
									.toJobParameters();

		jobLauncher.run(job, params);
		return new Response(processId, jobName, "Started");
	}
}
