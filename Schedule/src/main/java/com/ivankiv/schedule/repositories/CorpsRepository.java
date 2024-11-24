package com.ivankiv.schedule.repositories;

import com.ivankiv.schedule.entities.Corps;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CorpsRepository extends JpaRepository<Corps, Integer>{

    public Optional<Corps> findByid(int id);

}