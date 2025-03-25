package com.ivankiv.schedule.controllers;

import com.ivankiv.schedule.dto.LessonRequest;
import com.ivankiv.schedule.dto.LessonWebDTO;
import com.ivankiv.schedule.dto.ScheduleDTO;
import com.ivankiv.schedule.dto.ScheduleRequest;
import com.ivankiv.schedule.entities.LessonSchedule;
import com.ivankiv.schedule.entities.Schedule;
import com.ivankiv.schedule.exceptions.BadRequestException;
import com.ivankiv.schedule.exceptions.EntityNotFoundException;
import com.ivankiv.schedule.services.GroupService;
import com.ivankiv.schedule.services.LessonService;
import com.ivankiv.schedule.services.ScheduleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/web/admin")
public class AdminWebController {

    private final GroupService groupService;
    private final ScheduleService scheduleService;
    private final LessonService lessonService;
    private final String[] faculties = {"Факультет математики та інформатики", "Фізико-технічний факультет",
            "Факультет іноземних мов", "Факультет фізичної культури", "Факультет історії та права"};

    public AdminWebController(GroupService groupService, ScheduleService scheduleService, LessonService lessonService){
        this.groupService = groupService;
        this.scheduleService = scheduleService;
        this.lessonService = lessonService;
    }

    @GetMapping("/select")
    public String findSchedule(Model model, @RequestParam("group") String group, @RequestParam("date") LocalDate date){
        try{
            int groupId = groupService.getGroupId(group);

            try{
                ScheduleDTO schedule = scheduleService.get(groupId, date);
                model.addAttribute("group", group);
                model.addAttribute("date", date);
                model.addAttribute("lessons", schedule.lessons());
            }
            catch (EntityNotFoundException e) {
                model.addAttribute("notFoundSchedule", true);
            }
        }
        catch (EntityNotFoundException e) {
            model.addAttribute("notFoundGroup", true);
        }

        return "admin_select";
    }

    @GetMapping("/")
    public String adminPanel(Model model, @ModelAttribute(name = "error", binding = false) String error,
                             @ModelAttribute(name = "success", binding = false) String success) {
        model.addAttribute("error", error);
        model.addAttribute("success", success);

        return "admin_panel";
    }

    @GetMapping("/create")
    public String createSchedule(Model model, HttpSession session,
                                 @RequestParam(value = "group", required = false) String group,
                                 @RequestParam(value = "date", required = false) LocalDate date,
                                 @RequestParam(name = "invalid_teacher", required = false) boolean invalid_teacher  ) {

        if(group!=null && date!=null) {
            int groupId;
            try {
                groupId = groupService.getGroupId(group);
            } catch (EntityNotFoundException e) {
                model.addAttribute("error", "Такої групи не існує!");
                return "admin_panel";
            }

            if (scheduleService.exists(groupId, date)) {
                model.addAttribute("exists", true);/**/
            } else {
                Map<Integer, LessonWebDTO> lessons = new HashMap<>();
                session.setAttribute("lessons", lessons);
                session.setAttribute("groupId", groupId);
                session.setAttribute("group", group);
                session.setAttribute("date", date);
                List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
                session.setAttribute("free_numbers", numbers);
                session.setAttribute("method", "create");
                model.addAttribute("numbers", numbers);
            }
        }

        else if(session.getAttribute("lessons") == null){
            return "redirect:/";

        }
        else {
            group = (String)(session.getAttribute("group"));
            date = (LocalDate)(session.getAttribute("date"));
            List<Integer> numbers = (List<Integer>)(session.getAttribute("free_numbers"));
            Map<Integer, LessonWebDTO> lessons = (Map<Integer, LessonWebDTO>)(session.getAttribute("lessons"));
            model.addAttribute("numbers", numbers);
            model.addAttribute("lessons", lessons.values());
            if(invalid_teacher) {
                model.addAttribute("invalid_teacher", true);
            }
        }

        model.addAttribute("faculties", faculties);
        model.addAttribute("group", group);
        model.addAttribute("date", date);
        model.addAttribute("method", "create");

        return "admin_editor";
    }

    @PostMapping("/add")
    public String addLesson(Model model, HttpSession session,
                            @ModelAttribute LessonWebDTO lesson, RedirectAttributes redirectAttributes,
                            @RequestParam(value = "old_number", required = false) Integer oldNumber){
        if(session.getAttribute("lessons") == null){
            return "redirect:/";
        }
        String method = (String)session.getAttribute("method");
        List<Integer> numbers = (List<Integer>)(session.getAttribute("free_numbers"));
        Map<Integer, LessonWebDTO> lessons = (Map<Integer, LessonWebDTO>)(session.getAttribute("lessons"));

        Integer teacherId = lessonService.getTeacherId(lesson.teacher_faculty, lesson.teacher_surname, lesson.teacher_name);
        if(teacherId != null) {
            lesson.teacher_id = teacherId;
            lessons.put(lesson.number, lesson);
            numbers.remove( (Integer)(lesson.number) );
            if(oldNumber != null && oldNumber != lesson.number){
                lessons.remove(oldNumber);
            }
        }
        else{
            redirectAttributes.addFlashAttribute("invalidTeacher", true);
        }

        return "redirect:/web/admin/"+method+"#"+lesson.number;
    }

    @GetMapping("/remove")
    public String removeLesson(Model model, HttpSession session, @RequestParam("n") int n){
        if(session.getAttribute("lessons") == null){
            return "redirect:/";
        }
        Map<Integer, LessonWebDTO> lessons = (Map<Integer, LessonWebDTO>)(session.getAttribute("lessons"));
        List<Integer> numbers = (List<Integer>) (session.getAttribute("free_numbers"));
        String method = (String)session.getAttribute("method");

        lessons.remove(n);
        numbers.add(n);

        return "redirect:/web/admin/"+method;
    }

    @GetMapping("/create/save")
    public String saveSchedule(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("lessons") == null) {
            return "redirect:/";
        }
        Map<Integer, LessonWebDTO> lessons = (Map<Integer, LessonWebDTO>)(session.getAttribute("lessons"));
        int groupId = (int)(session.getAttribute("groupId"));
        LocalDate date = (LocalDate)(session.getAttribute("date"));

        HashMap<Integer, LessonRequest> lessonRequests = new HashMap<>();
        for(LessonWebDTO l : lessons.values()){
            lessonRequests.put(l.number(), new LessonRequest(l.teacher_id, l.subject, l.corps, l.cabinet, l.type));
        }
        ScheduleRequest request = new ScheduleRequest(date, groupId, lessonRequests);
        try{
            scheduleService.add(request);
        }
        catch (BadRequestException e) {
            redirectAttributes.addFlashAttribute("error", "Такий розклад вже існує!");
        }
        redirectAttributes.addFlashAttribute("success", "Розклад успішно створено!");

        session.removeAttribute("groupId");
        session.removeAttribute("date");
        session.removeAttribute("lessons");

        return "redirect:/web/admin/";
    }

    @GetMapping("/cancel")
    public String cancelCreate(HttpSession session){
        session.removeAttribute("groupId");
        session.removeAttribute("date");
        session.removeAttribute("lessons");

        return "redirect:/web/admin/";
    }

    @GetMapping("/update")
    public String updateSchedule(Model model, HttpSession session,
                                 @RequestParam(value = "group", required = false) String group,
                                 @RequestParam(value = "date", required = false) LocalDate date,
                                 @RequestParam(name = "isConfirmed", required = false) boolean invalid_teacher  ) {

        if(group!=null && date!=null) {
            int groupId;
            try {
                groupId = groupService.getGroupId(group);
            } catch (EntityNotFoundException e) {
                model.addAttribute("error", "Такої групи не існує!");
                return "admin_panel";
            }
                Map<Integer, LessonWebDTO> lessons = new HashMap<>();
                session.setAttribute("lessons", lessons);
                session.setAttribute("groupId", groupId);
                session.setAttribute("date", date);
                List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
                session.setAttribute("free_numbers", numbers);
                session.setAttribute("method", "update");
                model.addAttribute("numbers", numbers);
        }

        else if(session.getAttribute("lessons") == null){
            return "redirect:/";

        }
        else {
            group = (String)(session.getAttribute("group"));
            date = (LocalDate)(session.getAttribute("date"));
            List<Integer> numbers = (List<Integer>)(session.getAttribute("free_numbers"));
            Map<Integer, LessonWebDTO> lessons = (Map<Integer, LessonWebDTO>)(session.getAttribute("lessons"));
            model.addAttribute("numbers", numbers);
            model.addAttribute("lessons", lessons.values());
            if(invalid_teacher) {
                model.addAttribute("invalid_teacher", true);
            }
        }

        model.addAttribute("faculties", faculties);
        model.addAttribute("group", group);
        model.addAttribute("date", date);
        model.addAttribute("method", "update");

        return "admin_editor";
    }

    @GetMapping("/update/save")
    public String saveUpdatedSchedule(Model model, HttpSession session, RedirectAttributes redirectAttributes) throws BadRequestException, EntityNotFoundException {
        if (session.getAttribute("lessons") == null) {
            return "redirect:/";
        }
        Map<Integer, LessonWebDTO> lessons = (Map<Integer, LessonWebDTO>)(session.getAttribute("lessons"));
        int groupId = (int)(session.getAttribute("groupId"));
        LocalDate date = (LocalDate)(session.getAttribute("date"));

        HashMap<Integer, LessonRequest> lessonRequests = new HashMap<>();
        for(LessonWebDTO l : lessons.values()){
            lessonRequests.put(l.number(), new LessonRequest(l.teacher_id, l.subject, l.corps, l.cabinet, l.type));
        }
        ScheduleRequest request = new ScheduleRequest(date, groupId, lessonRequests);
        scheduleService.update(request);

        redirectAttributes.addFlashAttribute("success", "Розклад успішно оновлено!");
        session.removeAttribute("groupId");
        session.removeAttribute("date");
        session.removeAttribute("lessons");

        return "redirect:/web/admin/";
    }


    @GetMapping("/delete")
    public String deleteSchedule(Model model, @RequestParam("group") String group, @RequestParam("date") LocalDate date, RedirectAttributes redirectAttributes) {

        try {
            int groupId = groupService.getGroupId(group);
            scheduleService.remove(groupId, date);
            redirectAttributes.addFlashAttribute("success", "Розклад успішно видалено!");
        }
        catch (EntityNotFoundException e){
            redirectAttributes.addFlashAttribute("error", "Такий розклад не існує!");
        }

        return "redirect:/web/admin/";
    }

    @GetMapping("/edit")
    public String editSchedule(Model model, HttpSession session,
                                 @RequestParam(value = "group", required = false) String group,
                                 @RequestParam(value = "date", required = false) LocalDate date,
                                 @RequestParam(name = "isConfirmed", required = false) boolean invalid_teacher  ) {

        if(group!=null && date!=null) {
            int groupId;
            try {
                groupId = groupService.getGroupId(group);
            } catch (EntityNotFoundException e) {
                model.addAttribute("error", "Такої групи не існує!");
                return "admin_panel";
            }

            try {
                Schedule schedule = scheduleService.getSchedule(groupId, date);

                Map<Integer, LessonWebDTO> lessons = new HashMap<>();
                List<Integer> numbers = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8));
                for(LessonSchedule ls : schedule.lessonSchedules){
                    LessonWebDTO lesson = lessonService.getLessonWebDTO(ls.number(), ls.lesson);
                    lessons.put(ls.number(), lesson);
                    numbers.remove((Integer)(ls.number()));
                }
                session.setAttribute("lessons", lessons);
                session.setAttribute("free_numbers", numbers);
                model.addAttribute("numbers", numbers);
                model.addAttribute("lessons", lessons.values());
            }
            catch (EntityNotFoundException e){
                model.addAttribute("error", "Такого розкладу не існує!");
                return "admin_panel";
            }
                session.setAttribute("groupId", groupId);
                session.setAttribute("group", group);
                session.setAttribute("date", date);
                session.setAttribute("method", "update");
        }

        else if(session.getAttribute("lessons") == null){
            return "redirect:/";

        }
        else {
            group = (String)(session.getAttribute("group"));
            date = (LocalDate)(session.getAttribute("date"));
            List<Integer> numbers = (List<Integer>)(session.getAttribute("free_numbers"));
            Map<Integer, LessonWebDTO> lessons = (Map<Integer, LessonWebDTO>)(session.getAttribute("lessons"));
            model.addAttribute("numbers", numbers);
            model.addAttribute("lessons", lessons.values());
            if(invalid_teacher) {
                model.addAttribute("invalid_teacher", true);
            }
        }

        model.addAttribute("faculties", faculties);
        model.addAttribute("group", group);
        model.addAttribute("date", date);
        model.addAttribute("method", "update");

        return "admin_editor";
    }









}
