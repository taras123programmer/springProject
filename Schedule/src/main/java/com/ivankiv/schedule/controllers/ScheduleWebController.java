package com.ivankiv.schedule.controllers;

import com.ivankiv.schedule.dto.ScheduleDTO;
import com.ivankiv.schedule.exceptions.EntityNotFoundException;
import com.ivankiv.schedule.services.GroupService;
import com.ivankiv.schedule.services.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class ScheduleWebController {

    private final GroupService groupService;
    private final ScheduleService scheduleService;
    private final String[] faculties = {"Факультет математики та інформатики", "Фізико-технічний факультет",
            "Факультет іноземних мов", "Факультет фізичної культури", "Факультет історії та права"};

    public ScheduleWebController(GroupService groupService, ScheduleService scheduleService){
        this.groupService = groupService;
        this.scheduleService = scheduleService;
    }

    @GetMapping({"/", "/web/group"})
    public String selectGroup(Model model){
        model.addAttribute("faculties", faculties);
        String date = LocalDate.now().toString();
        model.addAttribute("date", date);

        return "select_group";
    }

    @GetMapping("/web/schedule")
    public String getSchedule(@RequestParam("groupId") int groupId, @RequestParam("date") LocalDate date, Model model) {
        try {
            ScheduleDTO schedule = scheduleService.get(groupId, date);
            model.addAttribute("group", schedule.group());
            model.addAttribute("lessons", schedule.lessons());
        } catch (EntityNotFoundException e) {
            model.addAttribute("holiday", true);
        }

        model.addAttribute("groupId", groupId);
        int dayOfWeek = date.getDayOfWeek().getValue();
        LocalDate beginOfWeek = date.minusDays(dayOfWeek - 1);
        LocalDate[] daysOfWeek = new LocalDate[7];
        for (int i = 0; i < 7; i++) {
            daysOfWeek[i] = beginOfWeek.plusDays(i);
        }
        LocalDate lastWeek = date.minusDays(7);
        LocalDate nextWeek = date.plusDays(7);

        model.addAttribute("days", daysOfWeek);
        model.addAttribute("dayOfWeek", dayOfWeek);
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("date", date);
        model.addAttribute("nextWeek", nextWeek);
        model.addAttribute("lastWeek", lastWeek);


        return "schedule";
    }

}
