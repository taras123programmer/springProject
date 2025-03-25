package com.ivankiv.schedule.dto;

public record GroupDTO(String specialty, int number, int course) {
    public GroupDTO(String specialty, int number, int course) {
        this.specialty = specialty;
        this.number = number;
        this.course = course;
    }

    public String specialty() {
        return this.specialty;
    }

    public int number() {
        return this.number;
    }

    public int course() {
        return this.course;
    }
}
