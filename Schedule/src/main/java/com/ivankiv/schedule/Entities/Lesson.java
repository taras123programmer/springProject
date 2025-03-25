package com.ivankiv.schedule.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "`Couple`")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @OneToOne
    @JoinColumn(name = "teacher_id")
    public Teacher teacher;

    @OneToOne
    @JoinColumn(name = "corps")
    public Corps corps;

    public String object;
    public int cabinet;
    public int type;

    public Lesson(){}

    public Lesson(int teacherId, int corpsId, String object, int cabinet, int type) {
        this.teacher = new Teacher(teacherId);
        this.corps = new Corps(corpsId);
        this.object = object;
        this.cabinet = cabinet;
        this.type = type;
    }


}
