package com.example.demo.config;

import com.example.demo.model.StudentCsv;
import com.example.demo.processor.FirstItemProcessor;
import com.example.demo.writer.StudentItemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;

@Configuration
public class StudentBatchConfig {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final FirstItemProcessor itemProcessor;
    private final StudentItemWriter itemWriter;

    @Autowired
    public StudentBatchConfig(FirstItemProcessor itemProcessor, StudentItemWriter itemWriter) {
        this.itemProcessor = itemProcessor;
        this.itemWriter = itemWriter;
    }

    @Bean
    @Qualifier("studentJob")
    public Job studentJob(JobRepository jobRepository, @Qualifier("studentStep") Step firstChunkStep) {
        return new JobBuilder("studentJob", jobRepository)
                    .incrementer(new RunIdIncrementer())
                    .start(firstChunkStep)
                    .build();
    }

    @Bean
    @Qualifier("studentStep")
    public Step studentStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("studentItemReader") FlatFileItemReader itemReader) {
        return new StepBuilder("studentStep", jobRepository)
                    .<StudentCsv, StudentCsv>chunk(3, transactionManager)
                    .reader(itemReader)
                    .writer(this.itemWriter)
                    .build();
    }

    @Bean
    @Qualifier("studentItemReader")
    public FlatFileItemReader<StudentCsv> studentItemReader() {
        return new FlatFileItemReaderBuilder<StudentCsv>()
                .name("student")
                .resource(new FileSystemResource(new File("D:\\dev\\udemy\\springframework\\springframework\\704_spring-batch5_flatFile\\input\\students.csv")))
                .delimited().delimiter(",")
                .names("ID", "FirstName", "LastName", "Email")
                .targetType(StudentCsv.class)
                .linesToSkip(1)
                .build();
    }
}
