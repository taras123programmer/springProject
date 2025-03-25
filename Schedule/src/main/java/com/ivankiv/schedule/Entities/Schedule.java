package com.ivankiv.schedule.Entities;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public LocalDate date;

    @OneToOne
    @JoinColumn(name = "group_id")
    public Group group;

    @OneToMany(mappedBy="schedule", cascade = CascadeType.ALL)
    public List<LessonSchedule> lessonSchedules;

    public Schedule(LocalDate date, int groupId){
        this.date = date;
        this.group = new Group(groupId);
        this.lessonSchedules = new ArrayList<>();
    }

    public Schedule(){}

}
