package com.ivankiv.schedule.dto;
import java.time.LocalDate;
import java.util.TreeMap;

public record ScheduleDTO(LocalDate date, String group, TreeMap<Integer, LessonDTO> lessons) {}
