package com.hyvercode.springday.job.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobStepListener implements StepExecutionListener {

  @Override
  public void beforeStep(StepExecution stepExecution) {
    log.info(
      "SPStepListener - CALLED BEFORE STEP JOB EXC ID {}, STEP NAME {}",
      stepExecution.getJobExecutionId(),
      stepExecution.getStepName()
    );
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    log.info(
      "SPStepListener - CALLED AFTER STEP JOB EXC ID {}, STEP NAME {} ",
      stepExecution.getJobExecutionId(),
      stepExecution.getStepName()
    );
    return ExitStatus.COMPLETED;
  }
}

