package com.ivankiv.schedule.Repositories;

import com.ivankiv.schedule.Entities.LessonBegin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonBeginRepository extends JpaRepository<LessonBegin, Integer> {

    public LessonBegin findByNumber(int number);

}
