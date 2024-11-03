package com.example.demo.config;

import com.example.demo.model.ProductCsv;
import com.example.demo.model.ProductFieldSetMapper;
import com.example.demo.processor.FirstItemProcessor;
import com.example.demo.writer.ProductItemWriter;
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
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;

@Configuration
public class ProductBatchConfig {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final FirstItemProcessor itemProcessor;
    private final ProductItemWriter itemWriter;

    @Autowired
    public ProductBatchConfig(FirstItemProcessor itemProcessor, ProductItemWriter itemWriter) {
        this.itemProcessor = itemProcessor;
        this.itemWriter = itemWriter;
    }

    @Bean
    @Qualifier("productJob")
    public Job productJob(JobRepository jobRepository, @Qualifier("productStep") Step firstChunkStep) {
        return new JobBuilder("productJob", jobRepository)
                    .incrementer(new RunIdIncrementer())
                    .start(firstChunkStep)
                    .build();
    }

    @Bean
    @Qualifier("productStep")
    public Step productStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("productItemReader") FlatFileItemReader itemReader) {
        return new StepBuilder("productStep", jobRepository)
                    .<ProductCsv, ProductCsv>chunk(3, transactionManager)
                    .reader(itemReader)
                    .writer(this.itemWriter)
                    .build();
    }

    @Bean
    @Qualifier("productItemReader")
    public FlatFileItemReader<ProductCsv> productItemReader() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames("product_id", "product_name", "product_category", "product_price");

        DefaultLineMapper lineMapper = new DefaultLineMapper<ProductCsv>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new ProductFieldSetMapper());

        FlatFileItemReader itemReader = new FlatFileItemReader<ProductCsv>();
        itemReader.setLinesToSkip(1);
        itemReader.setResource(new FileSystemResource(new File("D:\\dev\\udemy\\springframework\\springframework\\704_spring-batch5_flatFile\\input\\Product_Details.csv")));
        itemReader.setLineMapper(lineMapper);

        return itemReader;
    }
}
