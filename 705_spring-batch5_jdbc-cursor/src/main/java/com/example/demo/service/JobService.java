package com.example.demo.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final ApplicationContext applicationContext;
    private final JobLauncher jobLauncher;

    @Autowired
    public JobService(ApplicationContext applicationContext, JobLauncher jobLauncher) {
        this.applicationContext = applicationContext;
        this.jobLauncher = jobLauncher;
    }

    @Async
    public void startJob(String jobName) throws Exception {
        Job job = this.applicationContext.getBean(jobName, Job.class);
        JobParameters params = new JobParametersBuilder()
                                    .addLong("processId", System.currentTimeMillis())
                                    .toJobParameters();

        JobExecution jobExecution = this.jobLauncher.run(job, params);

        System.out.println("id: " + jobExecution.getId());
        System.out.println("jobId: " + jobExecution.getJobId());
    }
}
