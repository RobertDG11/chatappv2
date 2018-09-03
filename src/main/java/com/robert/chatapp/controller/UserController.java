package com.robert.chatapp.controller;

import com.robert.chatapp.dto.ListUserDto;
import com.robert.chatapp.dto.RegisterUserDto;
import com.robert.chatapp.dto.UserDtoConversions;
import com.robert.chatapp.entity.User;

import com.robert.chatapp.registration.OnRegistrationCompleteEvent;
import com.robert.chatapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("/findByGroupId")
    public List<ListUserDto> findByGroupId(@RequestParam("gid") Long gid) {

        return userService.getAllUsersInGroup(gid).stream().
                map(user -> userDtoConversions.convertToDto(user)).
                collect(Collectors.toList());
    }

    @GetMapping("/findByMessageIdAndGroupId")
    public ResponseEntity findByMessageIdAndGroupId(@RequestParam("mid") Long mid,
                                          @RequestParam("gid") Long gid) {

        User user = userService.getUserByMessageInGroup(mid, gid);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(userDtoConversions.convertToDto(user));
    }

    @GetMapping("/findAll")
    public List<ListUserDto> findAll() {

        List<ListUserDto> allUsers = userService.getAllUsers().stream().
                map(user -> userDtoConversions.convertToDto(user)).
                collect(Collectors.toList());

        allUsers.remove(0);

        return allUsers;
    }

    @PostMapping("/signUp")
    public ResponseEntity<Object> createUser(@Valid @RequestBody RegisterUserDto user,
                                             final HttpServletRequest request) {

        User savedUser = userService.createUser(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(savedUser, request.getLocale(), getAppUrl(request)));

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity deleteUserById(@RequestParam("id") Long id) {

        User user = userService.deleteUser(id);

        return new ResponseEntity<>(userDtoConversions.convertToDto(user), HttpStatus.OK);
    }

    @PutMapping("/updateUser")
    public ResponseEntity updateUser(@Valid @RequestBody RegisterUserDto user) {

        User newUser = userService.editUser(user);

        return new ResponseEntity<>(userDtoConversions.convertToDto(newUser), HttpStatus.OK);
    }

    @GetMapping(value = "/registrationConfirm")
    public ResponseEntity<String> confirmRegistration(@RequestParam("token") final String token) {

        String result = userService.validateVerificationToken(token);

        if (result.equals("valid")) {

            return new ResponseEntity<>("Registration confirmed successfully!", HttpStatus.OK);
        }

        if (result.equals("expired")) {

            return new ResponseEntity<>("Token expired. Please generate a new one!", HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>("Token is invalid!", HttpStatus.BAD_REQUEST);
    }


    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
