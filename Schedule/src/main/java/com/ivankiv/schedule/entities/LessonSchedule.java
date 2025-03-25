package com.ivankiv.schedule.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Couple_schedule")
public class LessonSchedule {

    @EmbeddedId
    public LessonScheduleId id;

    @ManyToOne
    @MapsId("scheduleId")
    @JoinColumn(name = "schedule_id")
    public Schedule schedule;

    @ManyToOne
    @MapsId("lessonId")
    @JoinColumn(name = "couple_id", updatable = false, insertable = false)
    public Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "number", insertable = false, updatable = false)
    public LessonBegin lessonBegin;


    @Embeddable
    public static class LessonScheduleId implements Serializable {

        @Column(name = "schedule_id")
        public int scheduleId;

        @Column(name = "couple_id")
        public int lessonId;

        @Column(name="number")
        public Integer number;

        public LessonScheduleId() {}

        public LessonScheduleId(int s, int l, int n) {
            this.lessonId = l;
            this.scheduleId = s;
            this.number = n;
        }
    }

    public LessonSchedule(Schedule schedule, Lesson lesson, int number){
        this.schedule = schedule;
        this.lesson = lesson;
        this.id = new LessonScheduleId(schedule.id, lesson.id, number);
    }

    public LessonSchedule() {}

    public int number(){
        return this.id.number;
    }

}
