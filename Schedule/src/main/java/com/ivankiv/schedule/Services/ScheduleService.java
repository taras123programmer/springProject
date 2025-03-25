package com.ivankiv.schedule.Services;

import com.ivankiv.schedule.DTO.LessonDTO;
import com.ivankiv.schedule.DTO.LessonRequest;
import com.ivankiv.schedule.DTO.ScheduleDTO;
import com.ivankiv.schedule.DTO.ScheduleRequest;
import com.ivankiv.schedule.Entities.Lesson;
import com.ivankiv.schedule.Entities.LessonBegin;
import com.ivankiv.schedule.Entities.LessonSchedule;
import com.ivankiv.schedule.Entities.Schedule;
import com.ivankiv.schedule.Exceptions.BadRequestException;
import com.ivankiv.schedule.Exceptions.EntityNotFoundException;
import com.ivankiv.schedule.Repositories.GroupRepository;
import com.ivankiv.schedule.Repositories.LessonBeginRepository;
import com.ivankiv.schedule.Repositories.LessonScheduleRepository;
import com.ivankiv.schedule.Repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class ScheduleService {

    private final ScheduleRepository repository;
    private final LessonService lessonService;
    private final LessonScheduleRepository lessonScheduleRepository;
    private final LessonBeginRepository lessonBeginRepository;
    private final GroupRepository groupRepository;
    private final ArrayList<String> types = new ArrayList<>(Arrays.asList("Лекція", "Практична", "Екзамен", "Семінар"));

    @Autowired
    public ScheduleService(GroupRepository groupRepository ,LessonBeginRepository lessonBeginRepository, LessonScheduleRepository lessonScheduleRepository, ScheduleRepository repository, GroupService groupService, LessonService lessonService) {
        this.repository = repository;
        this.lessonService = lessonService;
        this.lessonScheduleRepository = lessonScheduleRepository;
        this.lessonBeginRepository = lessonBeginRepository;
        this.groupRepository = groupRepository;
    }

    public ScheduleDTO getScheduleDTO(int group_id, LocalDate date) throws EntityNotFoundException {
        Schedule schedule = repository.getByGroupAndDate(group_id, date);
        if(schedule == null) throw new EntityNotFoundException("Such a schedule does not exist!");
        return EntityToDTO(schedule);
    }

    public ScheduleDTO EntityToDTO(Schedule entity) {
        ScheduleDTO dto = new ScheduleDTO(entity.date, entity.group.id, new TreeMap<Integer, LessonDTO>());
        for(LessonSchedule ls : entity.lessonSchedules) {
            Lesson lesson = ls.lesson;
            LessonDTO lessonDTO = new LessonDTO(lesson.object, lesson.teacher.surname, lesson.corps.name,
                    lesson.corps.getAddress(), lesson.cabinet, types.get(lesson.type - 1), ls.lessonBegin.begin);
            dto.lessons().put(ls.number(), lessonDTO);
        }
        return dto;
    }

    public Schedule saveSchedule(Schedule schedule) throws BadRequestException {
        try {
            return repository.saveAndFlush(schedule);
        }
        catch(DataIntegrityViolationException e) {
            throw new BadRequestException();
        }
    }

    @Transactional
    public ScheduleDTO add(ScheduleRequest scheduleRequest) throws BadRequestException {

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
            LessonBegin begin = lessonBeginRepository.findByNumber(n);
            if(begin == null){
                throw new BadRequestException("Invalid lesson number!");
                }

            LessonSchedule ls = new LessonSchedule(schedule, lesson, begin);
            schedule.lessonSchedules.add(ls);
        }
        schedule = saveSchedule(schedule);
        return EntityToDTO(schedule);
    }

    public Schedule getSchedule(int group_id, LocalDate date) throws EntityNotFoundException {
        Schedule schedule = repository.getByGroupAndDate(group_id, date);
        if(schedule != null) return schedule;
        else {
            throw new EntityNotFoundException("Such a schedule does not exist!");
        }
    }

    //@Transactional
    public void remove(int groupId, LocalDate date) throws EntityNotFoundException {
        Schedule schedule = getSchedule(groupId, date);
        deleteSchedule(schedule);
    }

    public void deleteSchedule(Schedule schedule) throws EntityNotFoundException {
        List<Lesson> oldLessons = new ArrayList<Lesson>();
        for(LessonSchedule ls : schedule.lessonSchedules) {
            oldLessons.add(ls.lesson);
        }
        repository.delete(schedule);

        for(Lesson lesson : oldLessons) {
            lessonService.delete(lesson);
        }
    }

    //@Transactional
    public ScheduleDTO update(ScheduleRequest scheduleRequest) throws BadRequestException, EntityNotFoundException {
        Schedule oldSchedule = repository.getByGroupAndDate(scheduleRequest.group_id(), scheduleRequest.date());
        if(oldSchedule!=null) {
            deleteSchedule(oldSchedule);
        }
        return add(scheduleRequest);
    }

    @Transactional
    public ScheduleDTO edit(ScheduleRequest request) throws BadRequestException {
        Schedule schedule = repository.getByGroupAndDate(request.group_id(),request.date());
        if(schedule == null) {
            throw new BadRequestException("Such a schedule does not exist!");
        }
        else {
            for(Map.Entry<Integer, LessonRequest> entry : request.lessons().entrySet()) {
                int k = entry.getKey();
                Lesson lesson = lessonService.addLesson(entry.getValue());

                LessonSchedule last = null;
                for(LessonSchedule ls : schedule.lessonSchedules) {
                    last = ls;
                    if(ls.number() == k) {
                        if(ls.lesson.id != lesson.id) {
                            schedule.lessonSchedules.remove(ls);
                        }
                        break;
                    }
                }
                if(last!=null && last.lesson.id == lesson.id) continue;
                LessonBegin begin = lessonBeginRepository.findByNumber(k);
                if(begin == null){
                    throw new BadRequestException("Invalid lesson number!");
                }
                LessonSchedule ls = new LessonSchedule(schedule, lesson, begin);
                schedule.lessonSchedules.add(ls);
            }

            return EntityToDTO(repository.save(schedule));
        }
    }

}

