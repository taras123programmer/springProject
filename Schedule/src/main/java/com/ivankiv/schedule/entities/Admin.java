package com.ivankiv.schedule.entities;

import jakarta.persistence.*;

@Entity
public class Admin {

    @Id
    int id;
    String username;
    String password;
    String email;

    public Admin() {};

    public String getUsername() {
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

}
