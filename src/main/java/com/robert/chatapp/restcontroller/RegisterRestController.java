package com.robert.chatapp.restcontroller;

import com.robert.chatapp.dto.RegisterUserDto;
import com.robert.chatapp.dto.UserDtoConversions;
import com.robert.chatapp.entity.User;
import com.robert.chatapp.entity.VerificationToken;
import com.robert.chatapp.exceptions.UserAlreadyActivatedException;
import com.robert.chatapp.registration.OnRegistrationCompleteEvent;
import com.robert.chatapp.service.IRegisterService;
import com.robert.chatapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class RegisterRestController {

    @Autowired
    private IRegisterService registerService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@Valid @RequestBody RegisterUserDto user,
                                             HttpServletRequest request) {

        User savedUser = registerService.createUser(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(savedUser, getAppUrl(request)));

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/register/confirm")
    public ResponseEntity<String> confirmRegistration(@RequestParam("token") final String token) {

        String result = registerService.validateVerificationToken(token);

        if (result.equals("valid")) {

            return new ResponseEntity<>("Registration confirmed successfully!", HttpStatus.OK);
        }

        if (result.equals("expired")) {

            return new ResponseEntity<>("Token expired. Please generate a new one!", HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>("Token is invalid!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/register/resend/{uid}")
    public ResponseEntity<String> resendConfirmToken(@PathVariable("uid") Long uid,
                                                     HttpServletRequest request) {

        User user = userService.getUser(uid);

        if (user.isActive()) {

            return new ResponseEntity<>("User already confirmed", HttpStatus.BAD_REQUEST);
        }

        registerService.generateNewVerificationToken(user);

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, getAppUrl(request)));

        return new ResponseEntity<>("A new confirmation link was sent to your email", HttpStatus.OK);
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
