package com.ivankiv.schedule.services;

import com.ivankiv.schedule.dto.GroupDTO;
import com.ivankiv.schedule.entities.Group;
import com.ivankiv.schedule.repositories.GroupRepository;
import com.ivankiv.schedule.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupService {

    private final GroupRepository repository;

    @Autowired
    public GroupService(GroupRepository r) {
        this.repository = r;
    }

    public int getGroupId(GroupDTO dto) throws EntityNotFoundException {
        Group group = repository.getBySpecialtyAndNumberAndCourse(dto.specialty(), dto.number(), dto.course());
        if(group == null) throw new EntityNotFoundException();
        return group.id;
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

