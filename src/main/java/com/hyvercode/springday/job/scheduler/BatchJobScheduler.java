package com.hyvercode.springday.job.scheduler;

import com.hyvercode.springday.model.entity.JobSchedule;
import com.hyvercode.springday.repository.JobScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class BatchJobScheduler {

  private final JobLauncher jobLauncher;
  private final Job job;
  private final JobScheduleRepository jobScheduleRepository;

  public BatchJobScheduler(JobLauncher jobLauncher, Job job, JobScheduleRepository jobScheduleRepository) {
    this.jobLauncher = jobLauncher;
    this.job = job;
    this.jobScheduleRepository = jobScheduleRepository;
  }

  @Scheduled(fixedRate = 60000) // Check every minute
  public void scheduleJob() throws Exception {
    Optional<JobSchedule> optionalJobSchedule = jobScheduleRepository.findTopByIsActiveTrueOrderByScheduledTimeAsc();
    if (optionalJobSchedule.isPresent()) {
      JobSchedule jobSchedule = optionalJobSchedule.get();
      if (shouldRunJob(jobSchedule)) {
        try {
          job.isRestartable();
          log.info("Run job {} ",jobSchedule.getJobName());
          JobParameters jobParameters = new JobParametersBuilder()
            .addString(jobSchedule.getJobName(), String.valueOf(System.currentTimeMillis()))
            .toJobParameters();
          jobLauncher.run(job, jobParameters);
          // Mark the job as executed
          jobSchedule.setExecuted(true);
          jobSchedule.setUpdatedBy("Batch System");
          jobScheduleRepository.save(jobSchedule);
        } catch (JobExecutionException e) {
          log.info("Failure execute job {} ", jobSchedule.getJobName());
          log.error("Error : {}", e.getLocalizedMessage());
        }
      }else{
        log.info("No found job at {} ",new Date().getTime());
      }
    }
  }

  private boolean shouldRunJob(JobSchedule jobSchedule) {
    LocalDateTime currentTime = LocalDateTime.now();
    return jobSchedule.getScheduledTime().isBefore(LocalTime.from(currentTime)) && !jobSchedule.isExecuted() && jobSchedule.getRetryCount() < 3;
  }
}
