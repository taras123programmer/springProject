package com.ivankiv.schedule.dto;

import java.sql.Time;

public record LessonDTO(String object, String teacher, String corps, String address, int cabinet, String type, Time begin){}
