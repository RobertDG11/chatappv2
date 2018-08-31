package com.robert.chatapp.service;

import com.robert.chatapp.dto.RegisterUserDto;
import com.robert.chatapp.dto.UserDtoConversions;
import com.robert.chatapp.entity.User;
import com.robert.chatapp.exceptions.EmailAlreadyExistsException;
import com.robert.chatapp.exceptions.UserNotFoundException;
import com.robert.chatapp.exceptions.UsernameAlreadyExistsException;
import com.robert.chatapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDtoConversions userDtoConversions;

    @Override
    @Transactional
    public User createUser(RegisterUserDto newUser) {

        if (userRepository.getUsersByUsername(newUser.getUsername()).isPresent()) {

            throw new UsernameAlreadyExistsException("There is an user with the same username. " +
                    "Please choose another one!");
        }


        if (userRepository.getUserByEmailAddress(newUser.getEmailAddress()).isPresent()) {

            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = userDtoConversions.convertToEntityRegister(newUser);

        user.setDateCreated(new Date());

        return userRepository.save(user);
    }

    @Override
    public User getUser(Long id) {

        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUser(Date dateCreated) {

        return userRepository.getUserByDateCreated(dateCreated).orElse(null);
    }

    @Override
    public User getUserByEmail(String emailAddress) {

        return userRepository.getUserByEmailAddress(emailAddress).orElse(null);
    }

    @Override
    public User getUserByPhone(String phoneNumber) {

        return userRepository.getUserByPhoneNumber(phoneNumber).orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {

        return userRepository.getUsersByUsername(username).orElse(null);
    }

    @Override
    @Transactional
    public User editUser(RegisterUserDto newUser) {

        Optional<User> oldUser = userRepository.findById(newUser.getId());

        oldUser.ifPresent(a -> updateValues(a, userDtoConversions.convertToEntityRegister(newUser)));

        return oldUser.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public User deleteUser(Long id) {

        Optional<User> userToDelete = userRepository.findById(id);

        userToDelete.ifPresent(user -> userRepository.delete(user));

        return userToDelete.orElseThrow(() -> new UserNotFoundException("User not found"));
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

        return userRepository.getUserByMessageIdAndGroupId(mid, gid).orElse(null);
    }

    private void updateValues(User oldUser, User newUser) {

        if (userRepository.getUsersByUsername(newUser.getUsername()).isPresent()) {

            throw new UsernameAlreadyExistsException("There is an user with the same username. " +
                    "Please choose another one!");
        }

        if (userRepository.getUserByEmailAddress(newUser.getEmailAddress()).isPresent()) {

            throw new EmailAlreadyExistsException("Email already exists");
        }

        oldUser.setFirstName(newUser.getFirstName());
        oldUser.setLastName(newUser.getLastName());
        oldUser.setUsername(newUser.getUsername());
        oldUser.setEmailAddress(newUser.getEmailAddress());
        oldUser.setPhoneNumber(newUser.getPhoneNumber());
        oldUser.setPassword(newUser.getPassword());
    }
}
