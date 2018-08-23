package com.robert.chatapp.controller;

import com.robert.chatapp.entity.User;
import com.robert.chatapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/list")
    public String listCustomers(Model model) {

        List<User> users = userRepository.findAll();
        users.remove(0);

        model.addAttribute("users", users);

        return "list-users";
    }

    @RequestMapping(value = "/findByGroupId", method = RequestMethod.GET)
    @ResponseBody
    public List<User> findByGroupId(@RequestParam("gid") Long gid) {
        return userRepository.getUsersByGroupId(gid);
    }

    @RequestMapping(value = "/findByMessageIdAndGroupId", method = RequestMethod.GET)
    @ResponseBody
    public User findByMessageIdAndGroupId(@RequestParam("mid") Long mid,
                                          @RequestParam("gid") Long gid) {

        return userRepository.getUserByMessageIdAndGroupId(mid, gid);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    public List<User> findAll() {

        return userRepository.findAll();
    }

}
