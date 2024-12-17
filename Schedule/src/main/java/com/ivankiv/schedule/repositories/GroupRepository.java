package com.ivankiv.schedule.repositories;

import com.ivankiv.schedule.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    boolean existsById(int id);

    Group getBySpecialtyAndCourseAndNumber(String specialty, int course, int number);

    @Query(value = "SELECT * FROM `Group` WHERE faculty=?1 AND course=?2", nativeQuery = true)
    List<Group> findAllByFacultyAndCourse(String faculty, int course);

}