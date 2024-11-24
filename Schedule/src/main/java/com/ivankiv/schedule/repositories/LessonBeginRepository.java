package com.ivankiv.schedule.repositories;

import com.ivankiv.schedule.entities.LessonBegin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonBeginRepository extends JpaRepository<LessonBegin, Integer> {

    public LessonBegin findByNumber(int number);

}
