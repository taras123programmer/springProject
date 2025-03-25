package com.ivankiv.schedule.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "`Couple`")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @OneToOne
    @JoinColumn(name = "teacher_id", updatable = false, insertable = false)
    public Teacher teacher;

    @Column(name="teacher_id")
    public int teacherId;

    @OneToOne
    @JoinColumn(name = "corps", updatable = false, insertable = false)
    public Corps corps;

    @Column(name = "corps")
    public int corpsId;

    public String object;
    public int cabinet;
    public int type;

    public Lesson(){}

    public Lesson(int teacherId, int corpsId, String object, int cabinet, int type) {
        this.teacherId = teacherId;
        this.corpsId = corpsId;
        this.object = object;
        this.cabinet = cabinet;
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
            if ((obj instanceof Lesson) ){
                return (this.id == ((Lesson)obj).id);
            }
            else{
                return false;
            }
    }
    @Override
    public int hashCode() {
        return Objects.hash(id); // Порівняння лише за полем id, якщо це єдине важливе поле
    }
}
