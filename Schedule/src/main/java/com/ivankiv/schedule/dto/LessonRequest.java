package com.ivankiv.schedule.dto;

public record LessonRequest(int teacherId, String object, int corpsId, int cabinet, String type){}
