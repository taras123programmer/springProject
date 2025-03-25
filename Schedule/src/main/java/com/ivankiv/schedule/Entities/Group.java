package com.ivankiv.schedule.Entities;

import com.ivankiv.schedule.DTO.GroupDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "`Group`")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String specialty;
    public int number;
    public int course;

    public Group(int groupId) {
        this.id = groupId;
    }

    public GroupDTO getDTO() {
        return new GroupDTO(this.specialty, this.number, this.course);
    }

    public Group(){}

    public Group(String specialty, int number, int course){
        this.specialty = specialty;
        this.number = number;
        this.course = course;
    }


}
