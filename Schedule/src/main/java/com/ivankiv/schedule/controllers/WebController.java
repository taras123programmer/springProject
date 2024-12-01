package com.ivankiv.schedule.controllers;

import com.ivankiv.schedule.dto.ScheduleDTO;
import com.ivankiv.schedule.exceptions.EntityNotFoundException;
import com.ivankiv.schedule.services.GroupService;
import com.ivankiv.schedule.services.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @GetMapping("/group")
    public String selectGroup(@RequestParam(required = false) String group, Model model) throws EntityNotFoundException {
        if(group == null){
        return "select_group";
        }
        else {
            Pattern p = Pattern.compile("([\\u0400-\\u04FF]+)-((\\d)\\d)");
            Matcher m = p.matcher(group);
            if(m.find()){
                String groupName = m.group(1);
                int groupNumber = Integer.parseInt(m.group(2));
                int groupCourse = Integer.parseInt(m.group(3));

                try {
                    int groupId = groupService.getGroupId(groupName, groupCourse, groupNumber);
                    String date = LocalDate.now().toString();

                    return String.format("redirect:/web/schedule?groupId=%d&date=%s", groupId, date);
                }
                catch (EntityNotFoundException e) {
                    model.addAttribute("notFoundGroup", true);
                    return "select_group";
                }

            }
            else{
                model.addAttribute("notFoundGroup", true);
            }
            return "select_group";
        }
    }

    @GetMapping("/schedule")
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
        LocalDate lastWeek = date.minusWeeks(7);
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
