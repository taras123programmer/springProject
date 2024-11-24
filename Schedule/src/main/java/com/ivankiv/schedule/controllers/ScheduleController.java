package com.ivankiv.schedule.controllers;

import com.ivankiv.schedule.dto.ScheduleDTO;
import com.ivankiv.schedule.dto.ScheduleRequest;
import com.ivankiv.schedule.exceptions.BadRequestException;
import com.ivankiv.schedule.exceptions.EntityNotFoundException;
import com.ivankiv.schedule.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService service;

    @Autowired
    ScheduleController(ScheduleService scheduleService){
        this.service = scheduleService;
    }

    @GetMapping
    public ResponseEntity<ScheduleDTO> getSchedule(@RequestParam int group_id, @RequestParam String date) throws EntityNotFoundException {
        ScheduleDTO schedule = service.get(group_id, LocalDate.parse(date));
        return ResponseEntity.status(HttpStatus.OK).body(schedule);
    }

    @PostMapping
    public ResponseEntity<Void> addSchedule(@RequestBody ScheduleRequest schedule) throws BadRequestException {
        service.add(schedule);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeSchedule(@RequestParam int group_id, @RequestParam LocalDate date) throws EntityNotFoundException {
        service.remove(group_id, date);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateSchedule(@RequestBody ScheduleRequest scheduleRequest) throws BadRequestException, EntityNotFoundException {
        service.update(scheduleRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping
    public ResponseEntity<Void> editSchedule(@RequestBody ScheduleRequest request) throws BadRequestException, EntityNotFoundException{
        service.edit(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}



