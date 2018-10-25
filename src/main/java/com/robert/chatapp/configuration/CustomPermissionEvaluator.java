package com.robert.chatapp.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/*
    https://www.baeldung.com/spring-security-create-new-custom-security-expression
 */

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    //private Logger log = LoggerFactory.getLogger(CustomPermissionEvaluator.class);

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        System.out.format("User %s trying to acces with permission %s", authentication.getName(), permission.toString());

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }

    private boolean hasPrivilege(Authentication auth, String role, String permission) {

        return false;
    }
}
