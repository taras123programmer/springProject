package com.ivankiv.schedule.DTO;

import java.sql.Time;

public record LessonDTO(String object, String teacher, String corps, String address, int cabinet, String type, Time begin){}
