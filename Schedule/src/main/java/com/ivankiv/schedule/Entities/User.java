//package com.ivankiv.schedule.Entities;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//
//@Entity
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    private String name;
//    private String surname;
//    private String email;
//    private String password;
//
//    public User() {}
//
//    public User(String name, String surname, String email, String password) {
//        this.name = name;
//        this.surname = surname;
//        this.email = email;
//        this.password = password;
//    }
//
//    public void setName(String name) {this.name = name;}
//    public String getName() {return this.name;}
//
//    public void setSurname(String surname) {this.surname = surname;}
//    public String getSurname() {return this.surname;}
//
//    public void setEmail(String email) {this.email = email;}
//    public String getEmail() {return this.email;}
//
//    public void setPassword(String password) {this.password = password;}
//    public String getPassword() {return this.password;}
//
//    public UserDTO toDTO() {
//        return new UserDTO(this.id, this.name, this.surname, this.email, this.password);
//    }
//
//}
