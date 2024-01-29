package org.example.config;

import org.example.listener.JobExecutionStatusListener;
import org.example.tasklet.SimpleEndTasklet;
import org.example.tasklet.SimpleStartTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SimpleJobConfiguration {

    @Bean
    public Job simpleJob(
            @Qualifier("startStep") Step helloStep,
            @Qualifier("endStep") Step endStep,
            @Qualifier("myJobRepository") JobRepository jobRepository,
            JobExecutionStatusListener jobExecutionStatusListener) {
        return new JobBuilder("simpleJob")
                .repository(jobRepository)
                .start(helloStep)
                .next(endStep)
                .listener(jobExecutionStatusListener)
                .build();
    }

    @Bean
    public Step startStep(
            SimpleStartTasklet simpleTasklet,
            @Qualifier("myJobRepository") JobRepository jobRepository,
            @Qualifier("myTransactionManager") PlatformTransactionManager transactionManager) {
        return new StepBuilder("helloStep")
                .tasklet(simpleTasklet)
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public Step endStep(SimpleEndTasklet simpleTasklet, @Qualifier("myJobRepository") JobRepository jobRepository, @Qualifier("myTransactionManager") PlatformTransactionManager transactionManager) {
        return new StepBuilder("endStep")
                .tasklet(simpleTasklet)
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .build();
    }
}
