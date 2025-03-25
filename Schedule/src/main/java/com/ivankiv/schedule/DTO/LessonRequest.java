package com.ivankiv.schedule.DTO;

public record LessonRequest(int teacherId, String object, int corpsId, int cabinet, String type){}
