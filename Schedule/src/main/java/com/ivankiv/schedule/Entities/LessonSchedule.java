package com.ivankiv.schedule.Entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Couple_schedule")
public class LessonSchedule {

    @EmbeddedId
    public LessonScheduleId id;

    @ManyToOne
    @MapsId("schedule")
    @JoinColumn(name = "schedule_id")
    public Schedule schedule;

    @ManyToOne
    @MapsId("lesson")
    @JoinColumn(name = "couple_id")
    public Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "number", insertable = false, updatable = false)
    public LessonBegin lessonBegin;

//    @Column(name = "number")
//    @MapsId("number")
//    public Integer number;

    @Embeddable
    public static class LessonScheduleId implements Serializable {

        @ManyToOne
        public Schedule schedule;

        @ManyToOne
        public Lesson lesson;

        @Column(name="number")
        public Integer number;

        public LessonScheduleId() {}

        public LessonScheduleId(Schedule s, Lesson l, int n) {
            this.lesson = l;
            this.schedule = s;
            this.number = n;
        }
    }

    public LessonSchedule(Schedule s, Lesson l, LessonBegin lessonBegin) {
        this.id = new LessonScheduleId(s, l, lessonBegin.number);
        this.schedule = s;
        this.lesson = l;
        this.lessonBegin = lessonBegin;
    }

    public LessonSchedule() {
    }

    public int number(){
        return this.id.number;
    }

}
