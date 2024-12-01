package com.ivankiv.schedule.repositories;

import com.ivankiv.schedule.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    boolean existsById(int id);

    Group getBySpecialtyAndCourseAndNumber(String specialty, int course, int number);

}