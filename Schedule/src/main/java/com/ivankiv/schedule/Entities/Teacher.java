package com.ivankiv.schedule.Entities;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity

@Table(name = "Teacher")
public class Teacher {

    @Id
    public int id;
    public String name;
    public String surname;
    public String patronymic;

    public Teacher(int id){
        this.id = id;
    }

    public Teacher(){ }

}
