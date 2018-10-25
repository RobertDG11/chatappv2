package com.robert.chatapp.service;

import com.robert.chatapp.dto.RegisterUserDto;
import com.robert.chatapp.entity.User;
import com.robert.chatapp.entity.VerificationToken;

public interface IRegisterService {

    User createUser(RegisterUserDto newUser);
    void createVerificationTokenForUser(final User user, final String token);
    String validateVerificationToken(String token);
    void generateNewVerificationToken(User user);
}
