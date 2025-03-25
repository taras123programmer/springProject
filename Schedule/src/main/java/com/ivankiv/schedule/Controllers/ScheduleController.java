package com.ivankiv.schedule.Controllers;

import com.ivankiv.schedule.DTO.ScheduleDTO;
import com.ivankiv.schedule.DTO.ScheduleRequest;
import com.ivankiv.schedule.Exceptions.BadRequestException;
import com.ivankiv.schedule.Exceptions.EntityNotFoundException;
import com.ivankiv.schedule.Services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService service;

    @Autowired
    ScheduleController(ScheduleService scheduleService){
        this.service = scheduleService;
    }

    @GetMapping
    public ResponseEntity<ScheduleDTO> getSchedule(@RequestParam int group_id, @RequestParam String date) throws EntityNotFoundException {
        ScheduleDTO schedule = service.getScheduleDTO(group_id, LocalDate.parse(date));
        return ResponseEntity.status(HttpStatus.OK).body(schedule);
    }

    @PostMapping
    public ResponseEntity<ScheduleDTO> addSchedule(@RequestBody ScheduleRequest schedule) throws BadRequestException {
        ScheduleDTO res = service.add(schedule);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeSchedule(@RequestParam int group_id, @RequestParam LocalDate date) throws EntityNotFoundException {
        service.remove(group_id, date);
            return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<ScheduleDTO> updateSchedule(@RequestBody ScheduleRequest scheduleRequest) throws BadRequestException, EntityNotFoundException {
        ScheduleDTO res = service.update(scheduleRequest);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PatchMapping
    public ResponseEntity<ScheduleDTO> editSchedule(@RequestBody ScheduleRequest request) throws BadRequestException{
        ScheduleDTO res = service.edit(request);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}



