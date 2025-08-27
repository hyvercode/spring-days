package com.hyvercode.springday.job.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class InitConfig {

  @Bean(name = "initJob")
  public Job initJob(JobBuilderFactory jobBuilderFactory, Step step) {
    return jobBuilderFactory.get("initJob")
      .incrementer(new RunIdIncrementer())
      .start(step)
      .build();
  }

  @Bean
  public Step step(StepBuilderFactory stepBuilderFactory) {
    return stepBuilderFactory.get("step")
      .tasklet((contribution, chunkContext) -> {
        log.info("Executing batch job step...");
        return RepeatStatus.FINISHED;
      })
      .build();
  }
}

