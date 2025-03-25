package com.ivankiv.schedule.entities;

import jakarta.persistence.*;

import java.sql.Time;

@Entity
@Table(
        name = "Call_schedule"
)
public class LessonBegin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int number;
    public Time begin;

    public LessonBegin(int number){
        this.number = number;
    }

    public LessonBegin(){}

}
