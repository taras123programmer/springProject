package com.ivankiv.schedule.services;

import com.ivankiv.schedule.dto.GroupDTO;
import com.ivankiv.schedule.entities.Group;
import com.ivankiv.schedule.exceptions.EntityNotFoundException;
import com.ivankiv.schedule.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

;

@Service
public class GroupService {

    private final GroupRepository repository;

    @Autowired
    public GroupService(GroupRepository r) {
        this.repository = r;
    }

    public int getGroupId(String specialty, int course, int number) throws EntityNotFoundException {
        Group group = repository.getBySpecialtyAndCourseAndNumber(specialty, course, number);
        if(group == null) throw new EntityNotFoundException();
        return group.id;
    }

    public List<GroupDTO> getGroupList(String faculty, int course){
        List<Group> groups = repository.findAllByFacultyAndCourse(faculty, course);
        List<GroupDTO> res = new ArrayList<>();
        for(Group g : groups){
            res.add(entityToDTO(g));
        }
        return res;
    }

    public int getGroupId(String group) throws  EntityNotFoundException{
        Pattern p = Pattern.compile("([\\u0400-\\u04FF]+)-((\\d)\\d)");
        Matcher m = p.matcher(group);
        if(m.find()) {
            String specialty = m.group(1);
            int groupNumber = Integer.parseInt(m.group(2));
            int groupCourse = Integer.parseInt(m.group(3));

            return getGroupId(specialty, groupCourse, groupNumber);
        }
        else{
            throw new EntityNotFoundException();
        }
    }

    public Group getGroupById(int id) throws EntityNotFoundException {
        Optional<Group> gOptional = repository.findById(id);
        if(gOptional.isPresent()) {
            return gOptional.get();
        }
        else {
            throw new EntityNotFoundException();
        }
    }

    public boolean exists(int id) {
        return repository.existsById(id);
    }

    public GroupDTO entityToDTO(Group g) {
        return new GroupDTO(g.specialty, g.number, g.course);
    }

    public GroupDTO addGroup(GroupDTO groupDTO) {
        Group group = new Group(groupDTO.specialty(), groupDTO.number(), groupDTO.course());
        return entityToDTO(repository.save(group));
    }
    
}

