package com.robert.chatapp.configuration;

import com.robert.chatapp.entity.Group;
import com.robert.chatapp.entity.Privilege;
import com.robert.chatapp.entity.User;
import com.robert.chatapp.repository.PrivilegeRepository;
import com.robert.chatapp.service.IGroupService;
import com.robert.chatapp.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
    https://www.baeldung.com/spring-security-create-new-custom-security-expression
 */

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    IGroupService groupService;

    @Autowired
    IUserService userService;

    @Autowired
    PrivilegeRepository privilegeRepository;



    @Override
    public boolean hasPermission(@NotNull Authentication authentication,
                                 @NotNull Object targetDomainObject,
                                 Object permission) {

        System.out.format("User %s trying to access %s with permission %s\n", authentication.getName(),
                targetDomainObject.toString(), permission.toString());

        if (!(permission instanceof String)){
            return false;
        }

        return hasPrivilegeInGroup(authentication, targetDomainObject, permission.toString());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }

    private boolean hasPrivilegeInGroup(Authentication auth, Object targetDomainObject, String permission) {

        User user;
        String username;

        if (auth.getName().contains("@")) {
            user = userService.findUserByEmail(auth.getName());
            username = user.getEmailAddress();

        } else {
            user = userService.findUserByUsername(auth.getName());
            username = user.getUsername();
        }

        Group group = groupService.getGroupById((Long) targetDomainObject);

        if (!user.isInGroup(group)) {

            return false;
        }

        return privilegeRepository.getPrivilegesOfUser(user, group)
                .stream()
                .map(Privilege::getName)
                .collect(Collectors.toCollection(ArrayList::new))
                .contains(permission);
    }
}
