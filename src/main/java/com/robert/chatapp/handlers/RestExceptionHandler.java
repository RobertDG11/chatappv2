package com.robert.chatapp.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robert.chatapp.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.robert.chatapp.restcontroller")
public class RestExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> handleBadUsername(UsernameAlreadyExistsException e) {

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

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> invalidConfirmToken(InvalidTokenException e) {

        try {
            return ResponseEntity.badRequest().body(new ObjectMapper().writeValueAsString(e.getErrorDto()));
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }

        return null;
    }

    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<String> handleGroupNotFound(GroupNotFoundException e) {

        try {
            return ResponseEntity.badRequest().body(new ObjectMapper().writeValueAsString(e.getErrorDto()));
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }

        return null;
    }

    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<String> handleMessageNotFound(MessageNotFoundException e) {

        try {
            return ResponseEntity.badRequest().body(new ObjectMapper().writeValueAsString(e.getErrorDto()));
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }

        return null;
    }

    @ExceptionHandler(UserNotActivatedException.class)
    public ResponseEntity<String> handleUserNotActivated(UserNotActivatedException e) {

        try {
            return ResponseEntity.badRequest().body(new ObjectMapper().writeValueAsString(e.getErrorDto()));
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }

        return null;
    }
}
