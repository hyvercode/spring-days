package com.hyvercode.springday.repository;

import com.hyvercode.springday.model.entity.JobSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobScheduleRepository extends JpaRepository<JobSchedule, Long> {
  Optional<JobSchedule> findTopByIsActiveTrueOrderByScheduledTimeAsc();
}
