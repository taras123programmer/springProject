package com.ivankiv.schedule.services;

import com.ivankiv.schedule.dto.LessonRequest;
import com.ivankiv.schedule.dto.LessonWebDTO;
import com.ivankiv.schedule.entities.Lesson;
import com.ivankiv.schedule.exceptions.BadRequestException;
import com.ivankiv.schedule.repositories.CorpsRepository;
import com.ivankiv.schedule.repositories.LessonRepository;
import com.ivankiv.schedule.repositories.TeacherRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class LessonService {

    private final LessonRepository repository;
    private final TeacherRepository teacherRepository;
    private final CorpsRepository corpsRepository;
    private final ArrayList<String> types = new ArrayList<>(Arrays.asList("Лекція", "Практична", "Екзамен", "Семінар"));

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public LessonService(LessonRepository lessonRepository,TeacherRepository teacherRepository, CorpsRepository corpsRepository) {
        this.repository = lessonRepository;
        this.teacherRepository = teacherRepository;
        this.corpsRepository = corpsRepository;
    }

    public Integer getTeacherId(String faculty, String surname, String name){
        return teacherRepository.findIdByFacultyAndNameAndSurname(faculty, name, surname);
    }

    public boolean isTeacherExists(int teacher_id) {
        return teacherRepository.existsById(teacher_id);
    }

    public boolean isCoupleExists(int couple_id) {
        return corpsRepository.existsById(couple_id);
    }

    @Transactional
    public Lesson addLesson(LessonRequest lessonRequest) throws BadRequestException {
        if(!isTeacherExists(lessonRequest.teacherId())){
            throw new BadRequestException("Invalid teacher id");
        }
        if(!isCoupleExists((lessonRequest.corpsId()))){
            throw new BadRequestException("Invalid couple id");
        }

        Lesson lesson = DTOtoEntity(lessonRequest);

        Integer id = repository.findId(lesson.object, lesson.teacherId, lesson.corpsId, lesson.cabinet, lesson.type);
        if(id != null){
            lesson.id = id;
            return entityManager.merge(lesson);
        }
        else{
            lesson = repository.save(lesson);
            return lesson;
        }
    }

    public Lesson DTOtoEntity(LessonRequest r) throws BadRequestException {
        int type = types.indexOf(r.type()) + 1;
        if(type != 0){
            return new Lesson(r.teacherId(), r.corpsId(), r.object(), r.cabinet(), type);
        }
        else {
            throw new BadRequestException("Invalid lesson type!");
        }
    }

    public LessonWebDTO getLessonWebDTO(int n, Lesson l){
        return new LessonWebDTO(n, l.object, l.teacher.faculty, l.teacher.name, l.teacher.surname, l.teacher.id,
                l.corpsId, l.cabinet, types.get(l.type - 1));
    }

    public void delete(Lesson lesson) {
        try {
            repository.delete(lesson);
        }
        catch (DataIntegrityViolationException e){}
    }


}

