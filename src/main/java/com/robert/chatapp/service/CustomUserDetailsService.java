package com.robert.chatapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        com.robert.chatapp.entity.User user;
        String username;

        if (s.contains("@")) {
            user = userService.findUserByEmail(s);
            username = user.getEmailAddress();

        } else {
            user = userService.findUserByUsername(s);
            username = user.getUsername();

        }

        List<SimpleGrantedAuthority> authList = getAuthorities("ROLE_ADMIN");

        return new User(username, user.getPassword(), authList);
    }

    private List<SimpleGrantedAuthority> getAuthorities(String role) {
        List<SimpleGrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority(role));

        return authList;
    }
}
