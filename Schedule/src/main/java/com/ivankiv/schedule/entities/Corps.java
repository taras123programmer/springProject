package com.ivankiv.schedule.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Corps")
public class Corps {

    @Id
    public int id;

    public String name;
    public String street;
    public int number;

    public Corps(int id){
        this.id = id;
    }

    public String getAddress(){
        return this.street + ' ' + this.number;
    }

    public Corps(){}

}
