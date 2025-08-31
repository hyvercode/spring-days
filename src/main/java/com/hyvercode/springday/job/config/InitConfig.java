package com.hyvercode.springday.job.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class InitConfig {


  @Bean(name = "initJob")
  public Job initJob(JobRepository jobRepository, Step step) {
    return new JobBuilder("initJob",jobRepository)
      .incrementer(new RunIdIncrementer())
      .start(step)
      .build();
  }

  @Bean
  public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new StepBuilder("step", jobRepository)
      .tasklet((contribution, chunkContext) -> {
        log.info("Executing batch job step...");
        return RepeatStatus.FINISHED;
      }, transactionManager)
      .build();
  }
}

