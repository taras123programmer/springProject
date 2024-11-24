package com.ivankiv.schedule.controllers;

import com.ivankiv.schedule.services.GroupService;
import com.ivankiv.schedule.services.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class WebController {

    private final GroupService groupService;
    private final ScheduleService scheduleService;

    public WebController(GroupService groupService, ScheduleService scheduleService){
        this.groupService = groupService;
        this.scheduleService = scheduleService;
    }

    @GetMapping()
    public String hello(Model model){
        model.addAttribute("a","World!");
        return "hello";
    }

}
