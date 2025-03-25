package com.ivankiv.schedule.DTO;
import java.time.LocalDate;
import java.util.TreeMap;

public record ScheduleDTO(LocalDate date, int group_id, TreeMap<Integer, LessonDTO> lessons) {}
