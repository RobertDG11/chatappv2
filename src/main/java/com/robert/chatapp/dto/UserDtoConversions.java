package com.robert.chatapp.dto;

import com.robert.chatapp.entity.User;
import com.robert.chatapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class UserDtoConversions {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    public ListUserDto convertToDto(User user) {

        return modelMapper.map(user, ListUserDto.class);
    }

    public User convertToEntity(ListUserDto listUserDto) throws ParseException {

        User user = modelMapper.map(listUserDto, User.class);;

        if (listUserDto.getId() != null) {
            User oldUser = userService.getUser(listUserDto.getId());
        }

        return user;
    }
}
