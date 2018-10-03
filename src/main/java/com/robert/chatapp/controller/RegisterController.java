package com.robert.chatapp.controller;

import com.robert.chatapp.dto.RegisterUserDto;
import com.robert.chatapp.dto.UserDtoConversions;
import com.robert.chatapp.entity.User;
import com.robert.chatapp.exceptions.EmailAlreadyExistsException;
import com.robert.chatapp.exceptions.UsernameAlreadyExistsException;
import com.robert.chatapp.registration.OnRegistrationCompleteEvent;
import com.robert.chatapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class RegisterController {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserDtoConversions userDtoConversions;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("/register")
    public ModelAndView showRegPage(ModelAndView modelAndView,
                                    RegisterUserDto registerUserDto) {

        modelAndView.addObject("user", registerUserDto);
        modelAndView.setViewName("register");

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView signUpUser(ModelAndView modelAndView,
                                   @Valid RegisterUserDto registerUserDto,
                                   BindingResult bindingResult,
                                   HttpServletRequest request) {

        User newUser = null;
        try {
            newUser = userService.createUser(registerUserDto);

        } catch (UsernameAlreadyExistsException | EmailAlreadyExistsException e) {

            bindingResult.reject("email");
        }

        if (bindingResult.hasErrors()) {

            modelAndView.setViewName("register");
        } else {

            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(newUser,
                    request.getLocale(), getAppUrl(request)));

            modelAndView.addObject("confirmationMessage",
                    "A confirmation e-mail has been sent to " + newUser.getEmailAddress());
            modelAndView.setViewName("register");
        }

        return modelAndView;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
