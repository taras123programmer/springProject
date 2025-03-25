package com.ivankiv.schedule.repositories;

import com.ivankiv.schedule.entities.LessonSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonScheduleRepository extends JpaRepository<LessonSchedule, Integer>{}
