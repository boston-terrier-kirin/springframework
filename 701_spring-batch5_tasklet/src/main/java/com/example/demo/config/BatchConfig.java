package com.example.demo.config;

import com.example.demo.listener.FirstJobListener;
import com.example.demo.listener.FirstStepListener;
import com.example.demo.service.SecondTasklet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final SecondTasklet secondTasklet;
    private final FirstJobListener firstJobListener;
    private final FirstStepListener firstStepListener;

    @Autowired
    public BatchConfig(SecondTasklet secondTasklet, FirstJobListener firstJobListener, FirstStepListener firstStepListener) {
        this.secondTasklet = secondTasklet;
        this.firstJobListener = firstJobListener;
        this.firstStepListener = firstStepListener;
    }

    @Bean
    public Job firstJob(JobRepository jobRepository, @Qualifier("firstStep") Step firstStep, @Qualifier("secondStep") Step secondStep) {
        return new JobBuilder("First Job", jobRepository)
                    .incrementer(new RunIdIncrementer())
                    .listener(this.firstJobListener)
                    .start(firstStep)
                    .next(secondStep)
                    .build();
    }

    @Bean
    @Qualifier("firstStep")
    public Step firstStep(JobRepository jobRepository, @Qualifier("firstTasklet") Tasklet tasklet, PlatformTransactionManager transactionManager) {
        return new StepBuilder("First Step", jobRepository)
                    .listener(this.firstStepListener)
                    .tasklet(tasklet, transactionManager)
                    .build();
    }

    @Bean
    @Qualifier("firstTasklet")
    public Tasklet firstTasklet() {
        return (contribution, chunkContext) -> {
            logger.info("★Hello firstTasklet!");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    @Qualifier("secondStep")
    public Step secondStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("Second Step", jobRepository)
                    .tasklet(this.secondTasklet, transactionManager)
                    .build();
    }
}
