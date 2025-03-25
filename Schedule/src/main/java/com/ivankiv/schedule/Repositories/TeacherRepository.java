package com.ivankiv.schedule.Repositories;

import com.ivankiv.schedule.Entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer>{

    @Query(value = "SELECT * FROM teacher WHERE id = :id", nativeQuery = true)
    public Optional<Teacher> findById(@Param("id") int id);

}