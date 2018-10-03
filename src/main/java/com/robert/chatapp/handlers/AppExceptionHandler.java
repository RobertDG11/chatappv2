package com.robert.chatapp.handlers;

import com.robert.chatapp.exceptions.UsernameAlreadyExistsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice("com.robert.chatapp.controller")
public class AppExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ModelAndView handleUsernameAlreadyExists(UsernameAlreadyExistsException e) {

        ModelAndView mav = new ModelAndView();

        mav.addObject("exception", e);
        mav.addObject("errorMessage", e.getErrorDto().getMessage());
        mav.setViewName("register");

        return mav;
    }
}
