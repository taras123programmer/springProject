package com.ivankiv.schedule.Controllers;

import com.ivankiv.schedule.DTO.GroupDTO;
import com.ivankiv.schedule.Entities.Group;
import com.ivankiv.schedule.Exceptions.EntityNotFoundException;
import com.ivankiv.schedule.Services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GroupController {

    private final GroupService service;

    @Autowired
    GroupController(GroupService groupService){
        this.service = groupService;
    }

    @GetMapping("/groupId")
    public ResponseEntity<Integer> getGroupId(@RequestParam String specialty, @RequestParam int course, @RequestParam int number) throws EntityNotFoundException {
        int id = service.getGroupId(new GroupDTO(specialty, number, course));
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @GetMapping("/group/{id}")
    public GroupDTO getGroup(@PathVariable int groupId) throws EntityNotFoundException {
        Group group = service.getGroupById(groupId);
        return group.getDTO();
    }

    @PostMapping("/group")
    public ResponseEntity<GroupDTO> addGroup(@RequestBody GroupDTO groupDTO){
        GroupDTO g = service.addGroup(groupDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(g);
    }

    public static record GroupIdResponse(int groupId) {}


}

