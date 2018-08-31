package com.robert.chatapp.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robert.chatapp.exceptions.EmailAlreadyExistsException;
import com.robert.chatapp.exceptions.UserNotFoundException;
import com.robert.chatapp.exceptions.UsernameAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> handleBadPasswords(UsernameAlreadyExistsException e) {

        try {
            return ResponseEntity.badRequest().body(new ObjectMapper().writeValueAsString(e.getErrorDto()));
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }

        return null;
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExists(EmailAlreadyExistsException e) {

        try {
            return ResponseEntity.badRequest().body(new ObjectMapper().writeValueAsString(e.getErrorDto()));
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }

        return null;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException e) {

        try {
            return ResponseEntity.badRequest().body(new ObjectMapper().writeValueAsString(e.getErrorDto()));
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }

        return null;
    }
}
