package com.robert.chatapp.service;

import com.robert.chatapp.dto.RegisterUserDto;
import com.robert.chatapp.entity.User;
import com.robert.chatapp.entity.VerificationToken;

import java.util.Date;
import java.util.List;

public interface IUserService {

    User createUser(RegisterUserDto newUser);
    User getUser(Long id);
    User getUser(Date dateCreated);
    User getUserByEmail(String emailAddress);
    User getUserByPhone(String phoneNumber);
    User getUserByUsername(String username);
    User getUserByToken(String token);
    User editUser(RegisterUserDto oldUser);
    User deleteUser(Long id);
    List<User> getAllUsers();
    List<User> getAllUsers(int pageNumber, int pageSize);
    List<User> getAllUsers(String notificationType);
    List<User> getAllUsers(Boolean active);
    List<User> getAllUsersInGroup(Long gid);
    User getUserByMessageInGroup(Long mid, Long gid);
    void createVerificationTokenForUser(final User user, final String token);
    String validateVerificationToken(String token);
    VerificationToken generateNewVerificationToken(final String existingVerificationToken);


}
