package com.ivankiv.schedule.Repositories;

import com.ivankiv.schedule.Entities.Schedule;
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

    //Schedule save(Schedule s);

    @Query(value = "CALL insertSchedule(:date, :groupId);", nativeQuery = true)
    public Integer insert(@Param("date") LocalDate date, @Param("groupId") int groupId);

    public boolean existsByGroupIdAndDate(int groupId, LocalDate date);

    public Schedule save(Schedule s);

    public void deleteByid(int id);

}

