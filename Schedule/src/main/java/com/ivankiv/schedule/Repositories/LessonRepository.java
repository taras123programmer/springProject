package com.ivankiv.schedule.Repositories;


import com.ivankiv.schedule.Entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LessonRepository extends JpaRepository<Lesson, Integer>{

    @Query(value = "SELECT id FROM couple WHERE object=?1 AND teacher_id =?2 AND corps=?3 AND cabinet=?4 AND type=?5", nativeQuery = true)
    public Integer findId(String o, int t, int c, int cabinet, int type);

    @Transactional
    @Query(value = "CALL insertOrUpdateLesson(:object, :teacher_id, :corps_id, :cabinet, :type);", nativeQuery=true)
    public Integer insertAndGetId(
            @Param("object") String object,
            @Param("teacher_id") int teacherId,
            @Param("corps_id") int corpsId,
            @Param("cabinet") int cabinet,
            @Param("type") int type
    );

    Lesson save(Lesson l);

    public void deleteById(int id);
}
