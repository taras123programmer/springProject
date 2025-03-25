package com.ivankiv.schedule.Repositories;

import com.ivankiv.schedule.Entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    Group getBySpecialtyAndNumberAndCourse(String specialty, int number, int course);

    boolean existsById(int id);

}