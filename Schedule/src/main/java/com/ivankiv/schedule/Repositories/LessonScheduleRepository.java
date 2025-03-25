package com.ivankiv.schedule.Repositories;

import com.ivankiv.schedule.Entities.LessonSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonScheduleRepository extends JpaRepository<LessonSchedule, Integer>{}
