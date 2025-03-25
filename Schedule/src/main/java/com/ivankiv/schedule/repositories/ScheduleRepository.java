package com.ivankiv.schedule.repositories;

import com.ivankiv.schedule.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    @Query("SELECT s FROM Schedule s JOIN FETCH s.lessonSchedules l JOIN FETCH l.lesson	"
            + "WHERE s.group.id= :group AND s.date = :date")
    Schedule getByGroupAndDate(@Param("group") int group_id, @Param("date") LocalDate date);

    @Query(value = "select case when exists(SELECT s FROM Schedule s JOIN s.lessonSchedules WHERE s.group.id=:group AND s.date=:date)" +
            "then true else false end")
    public boolean existsByGroupIdAndDate(@Param("group") int groupId, @Param("date") LocalDate date);

    public Schedule save(Schedule s);

    public void deleteByid(int id);

}

