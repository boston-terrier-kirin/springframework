package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean("job1")
    public Job firstJob(JobRepository jobRepository,
                            @Qualifier("step1") Step step1,
                            @Qualifier("step2") Step step2,
                            @Qualifier("step3") Step step3,
                            @Qualifier("stepFinal") Step stepFinal) {

        return new JobBuilder("First Job", jobRepository)
                    .start(step1)
                        .on(ExitStatus.FAILED.getExitCode()).to(stepFinal)
                    .from(step1)
                        .on(ExitStatus.COMPLETED.getExitCode()).to(step2)
                    .from(step1)
                        .on(ExitStatus.FAILED.getExitCode()).to(stepFinal)
                    .from(step2)
                        .on(ExitStatus.COMPLETED.getExitCode()).to(step3)
                    .from(step2)
                        .on(ExitStatus.FAILED.getExitCode()).to(stepFinal)
                    .end()
                    .build();
    }

    @Bean
    @Qualifier("step1")
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)

                    .tasklet(step1Tasklet(), transactionManager)
                    .build();
    }

    private Tasklet step1Tasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> {
            boolean failure = false;
            if (failure) {
                throw new RuntimeException();
            }

            System.out.println("step1Tasklet");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    @Qualifier("step2")
    public Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step2", jobRepository)
                .tasklet(step2Tasklet(), transactionManager)
                .build();
    }

    private Tasklet step2Tasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> {
            boolean failure = false;
            if (failure) {
                throw new RuntimeException();
            }

            System.out.println("step2Tasklet");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    @Qualifier("step3")
    public Step step3(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step3", jobRepository)
                .tasklet(step3Tasklet(), transactionManager)
                .build();
    }

    private Tasklet step3Tasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> {
            System.out.println("step3Tasklet");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    @Qualifier("stepFinal")
    public Step stepFinal(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("stepFinal", jobRepository)
                .tasklet(stepFinalTasklet(), transactionManager)
                .build();
    }

    private Tasklet stepFinalTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> {
            System.out.println("stepFinalTasklet");
            return RepeatStatus.FINISHED;
        };
    }
}
