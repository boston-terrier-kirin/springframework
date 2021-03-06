package com.example.spring_batch.config;

import com.example.spring_batch.listener.FirstJobListener;
import com.example.spring_batch.listener.FirstStepListener;
import com.example.spring_batch.listener.SecondJobListener;
import com.example.spring_batch.service.SecondTasklet;
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

@Configuration
public class Sample2Job {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private SecondTasklet secondTasklet;

    @Autowired
    private SecondJobListener secondJobListener;

    @Bean(name = "secondJobLauncher")
    public JobLauncher jobLauncher(JobRepository jobRepository) {
        // 【不採用】
        // 多重起動しないように、concurrencyLimit = 1 に設定。
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
        simpleAsyncTaskExecutor.setConcurrencyLimit(1);

        // 【採用】
        // こっちもthreadPoolを1に設定。
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(1);
        threadPoolTaskExecutor.initialize();

        /**
         * ①SimpleAsyncTaskExecutorの場合
         * 　ブラウザからジョブをキック
         * 　ジョブが終わる前に、もう1回キック⇒ブラウザ側で待ちになる。
         * ②ThreadPoolTaskExecutor
         * 　ブラウザからジョブをキック
         * 　ジョブが終わる前に、もう1回キック⇒ブラウザ側で待ちにならならずキューイングしてくるっぽい。
         * 　ブラウザから立て続けにキックしたら、順番に実行してくれる。
         *
         * というわけで、ThreadPoolTaskExecutorを採用。
         */

        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(threadPoolTaskExecutor);

        return jobLauncher;
    }

    @Bean(name = "secondJob")
    public Job firstJob() {
        return this.jobBuilderFactory.get("Second Job")
                .incrementer(new RunIdIncrementer())
                .start(firstStep())
                .listener(secondJobListener)
                .build();
    }

    private Step firstStep() {
        return this.stepBuilderFactory.get("First Step")
                .tasklet(this.secondTasklet)
                .build();
    }
}
