package com.example.chunksteps.config;

import com.example.chunksteps.reader.CustomItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.List;

/**
 * @author Hjwasim
 */
@Configuration
public class MyBatchConfig {

    @Bean
    public CustomItemReader itemReader() {
        // data
        List<String> fruits = Arrays.asList("Mango", "Papaya", "Banana", "Guava", "Pineapple",
                "Watermelon", "Musk Melon", "Orange","Grapes");
        return new CustomItemReader(fruits);
    }

    @Bean(name = "jobStep1")
    public Step jobStep1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("jobStep1", jobRepository)
                .chunk(4, transactionManager)
                .reader(itemReader())
                .writer(chunk -> {
                    System.out.println("Chunk start");
                    System.out.println(chunk);
                    System.out.println("Chunk End");
                })
                .build();
    }

    @Bean(name = "myJob")
    public Job myJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("myJob", jobRepository).incrementer(new RunIdIncrementer())
                .start(jobStep1(jobRepository, transactionManager))
                .build();
    }

}
