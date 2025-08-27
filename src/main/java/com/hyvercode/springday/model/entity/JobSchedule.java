package com.hyvercode.springday.model.entity;

import com.hyvercode.springday.helpers.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "job_schedule")
public class JobSchedule extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "job_schedule_id")
  private Long jobScheduleId;
  @Column(name = "job_name", length = 36, nullable = false, unique = true)
  private String jobName;
  @Column(name = "scheduled_time")
  private LocalTime scheduledTime;
  @Column(name = "executed")
  private boolean executed;
  @Column(name = "is_active")
  private Boolean isActive;
  @Column(name = "retry_count")
  private int retryCount;
}
