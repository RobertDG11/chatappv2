package com.robert.chatapp.controller;

import com.robert.chatapp.dto.ListUserDto;
import com.robert.chatapp.dto.UserDtoConversions;
import com.robert.chatapp.entity.User;
import com.robert.chatapp.repository.UserRepository;

import com.robert.chatapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDtoConversions userDtoConversions;

    @RequestMapping(value = "/findByGroupId", method = RequestMethod.GET)
    @ResponseBody
    public List<ListUserDto> findByGroupId(@RequestParam("gid") Long gid) {

        return userService.getAllUsersInGroup(gid).stream().
                map(user -> userDtoConversions.convertToDto(user)).
                collect(Collectors.toList());
    }

    @RequestMapping(value = "/findByMessageIdAndGroupId", method = RequestMethod.GET)
    @ResponseBody
    public ListUserDto findByMessageIdAndGroupId(@RequestParam("mid") Long mid,
                                          @RequestParam("gid") Long gid) {

        return userDtoConversions.convertToDto(userService.getUserByMessageInGroup(mid, gid));
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    public List<ListUserDto> findAll() {

        List<ListUserDto> allUsers = userService.getAllUsers().stream().
                map(user -> userDtoConversions.convertToDto(user)).
                collect(Collectors.toList());

        allUsers.remove(0);

        return allUsers;
    }

    @PostMapping("/signUp")
    public ResponseEntity<Object> createUser(@RequestBody User user) {

        User savedUser = userService.createUser(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/deleteById")
    public void deleteUserById(@RequestParam("id") Long id) {

        userService.deleteUser(id);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody User user) {

        userService.deleteUser(user);
    }

}
