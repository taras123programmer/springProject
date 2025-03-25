package com.ivankiv.schedule.dto;

import java.time.LocalDate;
import java.util.HashMap;

public record ScheduleRequest(LocalDate date, Integer group_id, HashMap<Integer, LessonRequest> lessons) {}