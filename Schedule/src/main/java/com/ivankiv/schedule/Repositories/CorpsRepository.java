package com.ivankiv.schedule.Repositories;

import com.ivankiv.schedule.Entities.Corps;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CorpsRepository extends JpaRepository<Corps, Integer>{

    public Optional<Corps> findByid(int id);

}