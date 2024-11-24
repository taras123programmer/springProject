package com.ivankiv.schedule.services;

import com.ivankiv.schedule.dto.LessonDTO;
import com.ivankiv.schedule.dto.LessonRequest;
import com.ivankiv.schedule.dto.ScheduleDTO;
import com.ivankiv.schedule.dto.ScheduleRequest;
import com.ivankiv.schedule.entities.Lesson;
import com.ivankiv.schedule.entities.LessonSchedule;
import com.ivankiv.schedule.entities.Schedule;
import com.ivankiv.schedule.exceptions.BadRequestException;
import com.ivankiv.schedule.exceptions.EntityNotFoundException;
import com.ivankiv.schedule.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class ScheduleService {

    private final ScheduleRepository repository;
    private final LessonService lessonService;
    //private final LessonScheduleRepository lessonScheduleRepository;
    private final LessonBeginRepository lessonBeginRepository;
    //private final LessonRepository lessonRepository;
    private final GroupRepository groupRepository;
    private final ArrayList<String> types = new ArrayList<>(Arrays.asList("Лекція", "Практична", "Екзамен", "Семінар"));

    @Autowired
    public ScheduleService(GroupRepository groupRepository, LessonBeginRepository lessonBeginRepository,
                           ScheduleRepository repository, GroupService groupService, LessonService lessonService) {
        this.repository = repository;
        this.lessonService = lessonService;
        //this.lessonScheduleRepository = lessonScheduleRepository;
        this.lessonBeginRepository = lessonBeginRepository;
        this.groupRepository = groupRepository;
        //this.lessonRepository = lessonRepository;
    }

    public ScheduleDTO get(int group_id, LocalDate date) throws EntityNotFoundException {
        Schedule s = getSchedule(group_id, date);
        return EntityToDTO(s);
    }

    public ScheduleDTO EntityToDTO(Schedule entity) {
        ScheduleDTO dto = new ScheduleDTO(entity.date, entity.groupId, new TreeMap<Integer, LessonDTO>());
        for(LessonSchedule ls : entity.lessonSchedules) {
            Lesson lesson = ls.lesson;
            LessonDTO lessonDTO = new LessonDTO(lesson.object, lesson.teacher.surname, lesson.corps.name,
                    lesson.corps.getAddress(), lesson.cabinet, types.get(lesson.type - 1), ls.lessonBegin.begin);
            dto.lessons().put(ls.number(), lessonDTO);
        }
        return dto;
    }

    @Transactional
    public Schedule save(Schedule schedule) throws BadRequestException {
            return repository.save(schedule);
    }

    @Transactional
    public void add(ScheduleRequest scheduleRequest) throws BadRequestException {

        if(repository.existsByGroupIdAndDate(scheduleRequest.group_id(), scheduleRequest.date())){
            throw new BadRequestException("Such a schedule already exists!");
        }
        if(!groupRepository.existsById(scheduleRequest.group_id())){
            throw new BadRequestException(("Invalid group id"));
        }

        Schedule schedule = new Schedule(scheduleRequest.date(), scheduleRequest.group_id());

        for(Map.Entry<Integer, LessonRequest> entry : scheduleRequest.lessons().entrySet()) {
            Lesson lesson = lessonService.addLesson(entry.getValue());

            int n = entry.getKey();
            if(!lessonBeginRepository.existsById(n)){
                throw new BadRequestException("Invalid lesson number!");
            }

            LessonSchedule ls = new LessonSchedule(schedule, lesson, n);
            schedule.lessonSchedules.add(ls);
        }

        save(schedule);
    }

    public Schedule getSchedule(int group_id, LocalDate date) throws EntityNotFoundException {
        Schedule schedule = repository.getByGroupAndDate(group_id, date);
        if(schedule != null) return schedule;
        else {
            throw new EntityNotFoundException("Such a schedule does not exist!");
        }
    }


    public void remove(int groupId, LocalDate date) throws EntityNotFoundException {
        Schedule schedule = getSchedule(groupId, date);

        Set<Lesson> oldLessons = new HashSet<>();
        for (LessonSchedule ls : schedule.lessonSchedules) {
            oldLessons.add(ls.lesson);
        }

        deleteSchedule(schedule);
        for(Lesson l : oldLessons){
            lessonService.delete(l);
        }
    }

    @Transactional
    public void deleteSchedule (Schedule schedule){
        repository.delete(schedule);
    }

    public void update(ScheduleRequest scheduleRequest) throws BadRequestException, EntityNotFoundException {
        Schedule schedule = repository.getByGroupAndDate(scheduleRequest.group_id(), scheduleRequest.date());
        if (schedule == null) {
            add(scheduleRequest);
        }
        else {

            Set<Lesson> oldLessons = new HashSet<>();
            Map<Integer, LessonSchedule> lessons = new HashMap<>();
            for (LessonSchedule ls : schedule.lessonSchedules) {
                lessons.put(ls.number(), ls);
                oldLessons.add(ls.lesson);
            }
            schedule.lessonSchedules.clear();

            for (Map.Entry<Integer, LessonRequest> entry : scheduleRequest.lessons().entrySet()) {
                Lesson lesson = lessonService.addLesson(entry.getValue());
                oldLessons.remove(lesson);

                int n = entry.getKey();
                if (!lessonBeginRepository.existsById(n)) {
                    throw new BadRequestException("Invalid lesson number!");
                }

                if (lessons.containsKey(n) && lessons.get(n).lesson.id == lesson.id) {
                    schedule.lessonSchedules.add(lessons.get(n));
                } else {
                    LessonSchedule ls = new LessonSchedule(schedule, lesson, n);
                    schedule.lessonSchedules.add(ls);
                }
            }
            save(schedule);

            for (Lesson l : oldLessons) {
                lessonService.delete(l);
            }
        }

    }

    public void edit(ScheduleRequest request) throws BadRequestException, EntityNotFoundException {
        Schedule schedule = getSchedule(request.group_id(), request.date());
        Set<Lesson> oldLessons = new HashSet<>();
        Map<Integer, LessonSchedule> lessonScheduleMap = new HashMap<>();

        for(LessonSchedule ls : schedule.lessonSchedules) {
            lessonScheduleMap.put(ls.number(), ls);
        }

        for(Map.Entry<Integer, LessonRequest> entry : request.lessons().entrySet()) {
            int n = entry.getKey();
            if(!lessonBeginRepository.existsById(n)){
                throw new BadRequestException("Invalid lesson number!");
            }

            Lesson lesson = lessonService.addLesson(entry.getValue());

            if(lessonScheduleMap.containsKey(n)) {
                if (lessonScheduleMap.get(n).lesson.id == lesson.id) {
                    continue;
                } else {
                    oldLessons.add(lessonScheduleMap.get(n).lesson);
                }
            }
            LessonSchedule ls = new LessonSchedule(schedule, lesson, n);
            lessonScheduleMap.put(n, ls);
        }
        schedule.lessonSchedules.clear();
        schedule.lessonSchedules.addAll(lessonScheduleMap.values());
        save(schedule);

        for(Lesson l : oldLessons){
            lessonService.delete(l);
        }

    }

}

