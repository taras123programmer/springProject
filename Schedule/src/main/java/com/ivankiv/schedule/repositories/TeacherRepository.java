package com.ivankiv.schedule.repositories;

import com.ivankiv.schedule.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer>{

    public Optional<Teacher> findById(int id);

    @Query(value = "SELECT id FROM Teacher WHERE faculty=?1 and name=?2 and surname=?3", nativeQuery = true)
    Integer findIdByFacultyAndNameAndSurname(String faculty, String name, String surname);

}