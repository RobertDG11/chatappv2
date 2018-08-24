package com.robert.chatapp.service;

import com.robert.chatapp.entity.User;

import java.util.Date;
import java.util.List;

public interface IUserService {

    User createUser(User newUser);
    User getUser(Long id);
    User getUser(Date dateCreated);
    User getUserByEmail(String emailAddress);
    User getUserByPhone(String phoneNumber);
    User editUser(User oldUser);
    void deleteUser(Long id);
    void deleteUser(User oldUser);
    List<User> getAllUsers();
    List<User> getAllUsers(int pageNumber, int pageSize);
    List<User> getAllUsers(String notificationType);
    List<User> getAllUsers(Boolean active);
    List<User> getAllUsersInGroup(Long gid);
    User getUserByMessageInGroup(Long mid, Long gid);


}
