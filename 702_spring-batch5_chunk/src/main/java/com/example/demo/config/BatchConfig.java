package com.example.demo.config;

import com.example.demo.listener.FirstJobListener;
import com.example.demo.processor.FirstItemProcessor;
import com.example.demo.reader.FirstItemReader;
import com.example.demo.writer.FirstItemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final FirstJobListener firstJobListener;
    private final FirstItemReader itemReader;
    private final FirstItemProcessor itemProcessor;
    private final FirstItemWriter itemWriter;

    @Autowired
    public BatchConfig(FirstJobListener firstJobListener, FirstItemReader itemReader, FirstItemProcessor itemProcessor, FirstItemWriter itemWriter) {
        this.firstJobListener = firstJobListener;
        this.itemReader = itemReader;
        this.itemProcessor = itemProcessor;
        this.itemWriter = itemWriter;
    }

    @Bean
    public Job firstJob(JobRepository jobRepository, @Qualifier("firstChunkStep") Step firstChunkStep) {
        return new JobBuilder("First Job", jobRepository)
                    .incrementer(new RunIdIncrementer())
                    .listener(this.firstJobListener)
                    .start(firstChunkStep)
                    .build();
    }

    @Bean
    @Qualifier("firstChunkStep")
    public Step firstChunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("First Chunk Step", jobRepository)
                    .<Integer, Long>chunk(3, transactionManager)
                    .reader(this.itemReader)
                    .processor(this.itemProcessor)
                    .writer(this.itemWriter)
                    .build();
    }
}
