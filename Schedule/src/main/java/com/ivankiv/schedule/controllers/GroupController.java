package com.ivankiv.schedule.controllers;

import com.ivankiv.schedule.dto.GroupDTO;
import com.ivankiv.schedule.entities.Group;
import com.ivankiv.schedule.exceptions.EntityNotFoundException;
import com.ivankiv.schedule.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/group")
public class GroupController {

    private final GroupService service;

    @Autowired
    GroupController(GroupService groupService){
        this.service = groupService;
    }

    @GetMapping("groupId")
    public ResponseEntity<Integer> getGroupId(@RequestParam String specialty, @RequestParam int course, @RequestParam int number) throws EntityNotFoundException {
        int id = service.getGroupId(specialty, course, number);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

        @GetMapping("{id}")
    public GroupDTO getGroup(@PathVariable("id") int groupId) throws EntityNotFoundException {
        Group group = service.getGroupById(groupId);
        return group.getDTO();
    }

    @PostMapping
    public ResponseEntity<GroupDTO> addGroup(@RequestBody GroupDTO groupDTO){
        GroupDTO g = service.addGroup(groupDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(g);
    }


}

