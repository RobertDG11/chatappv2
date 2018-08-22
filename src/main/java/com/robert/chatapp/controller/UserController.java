package com.robert.chatapp.controller;

import com.robert.chatapp.entity.User;
import com.robert.chatapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    Logger log;

    @RequestMapping("/list")
    public String listCustomers(Model model) {

        List<User> users = userRepository.getAllUsers();
        users.remove(0);

        model.addAttribute("users", users);

        return "list-users";
    }

    @RequestMapping(value = "/findByGroup", method = RequestMethod.GET)
    @ResponseBody
    public List<User> users(@RequestParam("id") Long id) {
        return userRepository.getUsersByGroupId(id);

    }

}
