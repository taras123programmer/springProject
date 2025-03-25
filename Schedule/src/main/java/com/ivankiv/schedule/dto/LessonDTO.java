package com.ivankiv.schedule.dto;

import java.time.LocalTime;

public record LessonDTO(String object, String teacher, String corps, String address, int cabinet, String type, LocalTime begin){}
