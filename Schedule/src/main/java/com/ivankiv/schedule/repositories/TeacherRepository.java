package com.ivankiv.schedule.repositories;

import com.ivankiv.schedule.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer>{

    public Optional<Teacher> findById(int id);

}