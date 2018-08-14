package com.robert.chatapp.controller;

import com.robert.chatapp.entity.User;
import com.robert.chatapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/list")
    public String listCustomers(Model model) {

        List<User> users = userRepository.getAllUsers();
        users.remove(0);

        model.addAttribute("users", users);

        return "list-users";
    }

}
