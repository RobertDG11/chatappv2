package com.robert.chatapp.restcontroller;

import com.robert.chatapp.dto.ListUserDto;
import com.robert.chatapp.dto.RegisterUserDto;
import com.robert.chatapp.dto.UserDtoConversions;
import com.robert.chatapp.entity.User;

import com.robert.chatapp.entity.VerificationToken;
import com.robert.chatapp.registration.OnRegistrationCompleteEvent;
import com.robert.chatapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserDtoConversions userDtoConversions;

    @GetMapping("/find/{gid}")
    public List<ListUserDto> findByGroupId(@PathVariable("gid") Long gid) {

        return userService.getAllUsersInGroup(gid).stream().
                map(user -> userDtoConversions.convertToDto(user)).
                collect(Collectors.toList());
    }

    @GetMapping("/find/{mid}/{gid}")
    public ResponseEntity findByMessageIdAndGroupId(@PathVariable("mid") Long mid,
                                          @PathVariable("gid") Long gid) {

        User user = userService.getUserByMessageInGroup(mid, gid);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(userDtoConversions.convertToDto(user));
    }

    @GetMapping("/find")
    public List<ListUserDto> findAll() {


        return userService.getAllUsers().stream().
                map(user -> userDtoConversions.convertToDto(user)).
                collect(Collectors.toList());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUserById(@PathVariable("id") Long id) {

        User user = userService.deleteUser(id);

        return new ResponseEntity<>("User with id: " + id + " successfully deleted", HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity updateUser(@Valid @RequestBody RegisterUserDto user) {

        User newUser = userService.editUser(user);

        return new ResponseEntity<>(userDtoConversions.convertToDto(newUser), HttpStatus.OK);
    }

}
