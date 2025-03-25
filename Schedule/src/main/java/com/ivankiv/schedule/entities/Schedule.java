package com.ivankiv.schedule.entities;


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
    @JoinColumn(name = "group_id",updatable = false, insertable = false)
    public Group group;

    @Column(name = "group_id")
    public int groupId;

    @OneToMany(mappedBy="schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<LessonSchedule> lessonSchedules;

    public Schedule(LocalDate date, int groupId){
        this.date = date;
        this.groupId = groupId;
        this.lessonSchedules = new ArrayList<>();
    }

    public Schedule(){}

}
