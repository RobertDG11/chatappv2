package com.robert.chatapp.restcontroller;

import com.robert.chatapp.dto.GroupDto;
import com.robert.chatapp.dto.GroupDtoConversion;
import com.robert.chatapp.entity.Group;
import com.robert.chatapp.entity.User;
import com.robert.chatapp.service.IGroupService;
import com.robert.chatapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/group")
public class GroupRestController {

    @Autowired
    IGroupService groupService;

    @Autowired
    GroupDtoConversion groupDtoConversion;

    @Autowired
    IUserService userService;

    @PostMapping("/create")
    ResponseEntity<Object> createGroup(@RequestParam String name, @RequestParam Long createdBy) {

        Group savedGroup = groupService.createGroup(name, createdBy);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedGroup.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/addUser")
    ResponseEntity<Object> inertUser(@RequestParam Long gid, @RequestParam Long uid) {

        groupService.insertNewUser(gid, uid);

        User user = userService.getUser(uid);
        Group group = groupService.getGroupById(gid);

        return ResponseEntity.ok("User " + user.getUsername() + " joined " + group.getName());
    }

    @DeleteMapping("/removeUser")
    ResponseEntity<Object> removeUser(@RequestParam Long gid, @RequestParam Long uid) {

        groupService.removeUser(gid, uid);

        User user = userService.getUser(uid);
        Group group = groupService.getGroupById(gid);

        return ResponseEntity.ok("User " + user.getUsername() + " left " + group.getName());
    }

    @DeleteMapping("/deleteGroup/{gid}")
    ResponseEntity<Object> deleteGroup(@PathVariable("gid") Long gid) {

        Group group = groupService.getGroupById(gid);

        groupService.deleteGroup(gid);

        return ResponseEntity.ok("Group " + group.getName() + " successfully deleted!");
    }

    @GetMapping("/getGroupById/{gid}")
    ResponseEntity<Object> getGroupById(@PathVariable("gid") Long gid) {

        Group group = groupService.getGroupById(gid);
        GroupDto groupDto = groupDtoConversion.convertToDto(group);

        groupDto.setNumberOfUsers(group.getUsers().size());

        return ResponseEntity.ok(groupDto);

    }

    @GetMapping("/getAllGroups")
    ResponseEntity<Object> getAllGroups() {

        List<GroupDto> groups = groupService.getAllGroups();

        return ResponseEntity.ok(groups);
    }

    @PutMapping("/blockUser")
    ResponseEntity<Object> blockUser(@RequestParam Long gid, @RequestParam Long uid) {

        groupService.blockUser(gid, uid);

        User user = userService.getUser(uid);
        Group group = groupService.getGroupById(gid);

        return ResponseEntity.ok("User " + user.getUsername() +
                " has been blocked in group " + group.getName());
    }

    @PostMapping("/privateConversation")
    ResponseEntity<Object> createPrivateConversation(@RequestParam Long ownerId,
                                                     @RequestParam Long otherId) {

        Group group = groupService.createPrivateConversation(ownerId, otherId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(group.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

}
