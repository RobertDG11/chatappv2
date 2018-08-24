package com.robert.chatapp.service;

import com.robert.chatapp.entity.User;
import com.robert.chatapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public User createUser(User newUser) {

        newUser.setDateCreated(new Date());
        return userRepository.save(newUser);
    }

    @Override
    public User getUser(Long id) {

        return userRepository.getUserById(id);
    }

    @Override
    public User getUser(Date dateCreated) {

        return userRepository.getUserByDateCreated(dateCreated);
    }

    @Override
    public User getUserByEmail(String emailAddress) {

        return userRepository.getUserByEmailAddress(emailAddress);
    }

    @Override
    public User getUserByPhone(String phoneNumber) {

        return userRepository.getUserByPhoneNumber(phoneNumber);
    }

    @Override
    public User editUser(User oldUser) {
        return null;
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteUser(User oldUser) {

        userRepository.delete(oldUser);
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public List<User> getAllUsers(int pageNumber, int pageSize) {

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        return userRepository.findAll(pageRequest).getContent();
    }

    @Override
    public List<User> getAllUsers(String notificationType) {

        return userRepository.getUsersByNotificationType(notificationType);
    }

    @Override
    public List<User> getAllUsers(Boolean active) {

        return userRepository.getUsersByActive(active);
    }

    @Override
    public List<User> getAllUsersInGroup(Long gid) {

        return userRepository.getUsersByGroupId(gid);
    }

    @Override
    public User getUserByMessageInGroup(Long mid, Long gid) {

        return userRepository.getUserByMessageIdAndGroupId(mid, gid);
    }
}
