package com.ivankiv.schedule.Services;

import com.ivankiv.schedule.DTO.LessonRequest;
import com.ivankiv.schedule.Entities.Lesson;
import com.ivankiv.schedule.Exceptions.BadRequestException;
import com.ivankiv.schedule.Repositories.CorpsRepository;
import com.ivankiv.schedule.Repositories.LessonRepository;
import com.ivankiv.schedule.Repositories.TeacherRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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


    public boolean isTeacherExists(int teacher_id) {
        return teacherRepository.findById(teacher_id).isPresent();
    }

    public boolean isCoupleExists(int couple_id) {
        return corpsRepository.findById(couple_id).isPresent();
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

        Integer id = repository.findId(lesson.object, lesson.teacher.id, lesson.corps.id, lesson.cabinet, lesson.type);
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
    
    public void delete(Lesson l) {
        try {
            repository.delete(l);
            repository.flush();
        }
        catch (DataIntegrityViolationException e) {}
    }

}

