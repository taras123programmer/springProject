package com.ivankiv.schedule.repositories;


import com.ivankiv.schedule.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//@Transactional(noRollbackFor = DataIntegrityViolationException.class)
public interface LessonRepository extends JpaRepository<Lesson, Integer>{

    @Query(value = "SELECT id FROM couple WHERE object=?1 AND teacher_id =?2 AND corps=?3 AND cabinet=?4 AND type=?5", nativeQuery = true)
    public Integer findId(String o, int t, int c, int cabinet, int type);

    public void deleteById(int id);

}
