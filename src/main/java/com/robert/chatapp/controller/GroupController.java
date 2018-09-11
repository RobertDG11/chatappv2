package com.robert.chatapp.controller;

import com.robert.chatapp.dto.GroupDtoConversion;
import com.robert.chatapp.entity.Group;
import com.robert.chatapp.service.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    IGroupService groupService;

    @Autowired
    GroupDtoConversion groupDtoConversion;

    @PostMapping("/create")
    ResponseEntity<Object> createGroup(@RequestParam String name, @RequestParam Long createdBy) {

        Group savedGroup = groupService.createGroup(name, createdBy);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedGroup.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

}
