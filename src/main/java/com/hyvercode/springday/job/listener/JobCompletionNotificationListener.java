package com.hyvercode.springday.job.listener;

import com.hyvercode.springday.model.entity.JobSchedule;
import com.hyvercode.springday.repository.JobScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

  private final JobScheduleRepository jobScheduleRepository;

  public JobCompletionNotificationListener(JobScheduleRepository jobScheduleRepository) {
    this.jobScheduleRepository = jobScheduleRepository;
  }

  @Override
  public void beforeJob(JobExecution jobExecution) {
    log.info("Start Job {} ", jobExecution.getJobId());
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findTopByIsActiveTrueOrderByScheduledTimeAsc();
    if (jobScheduleOptional.isPresent()) {
      JobSchedule jobSchedule = jobScheduleOptional.get();
      jobSchedule.setExecuted(true);
      jobSchedule.setUpdatedBy("Batch System");
      jobScheduleRepository.save(jobSchedule);
      log.info("Completed job {} , job status {} ", jobExecution.getJobId(), jobExecution.getStatus());
    }
    log.info("Not found job {} , job status {} ", jobExecution.getJobId(), jobExecution.getStatus());
  }
}

