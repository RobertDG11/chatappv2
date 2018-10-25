package com.robert.chatapp.controller;

import com.robert.chatapp.dto.RegisterUserDto;
import com.robert.chatapp.entity.User;
import com.robert.chatapp.exceptions.EmailAlreadyExistsException;
import com.robert.chatapp.registration.OnRegistrationCompleteEvent;
import com.robert.chatapp.repository.UserRepository;
import com.robert.chatapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class RegisterController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("/register")
    public ModelAndView registration(){

        ModelAndView modelAndView = new ModelAndView();
        RegisterUserDto user = new RegisterUserDto();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register");

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView createNewUser(@Valid @ModelAttribute("user") RegisterUserDto user,
                                      BindingResult bindingResult,
                                      HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();

        User userExists = userService.findUserByEmail(user.getEmailAddress());

        if (userExists != null) {
            bindingResult
                    .rejectValue("emailAddress", "error.user",
                            "There is already a user registered with the email provided");
        }

        userExists = userService.findUserByUsername(user.getUsername());

        if (userExists != null) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("register");

        } else {

            //User savedUser = userService.createUser(user);

            //eventPublisher.publishEvent(new OnRegistrationCompleteEvent(savedUser, getAppUrl(request)));


            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new RegisterUserDto());
            modelAndView.setViewName("register");

        }
        return modelAndView;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
