package com.robert.chatapp.validations;

import com.robert.chatapp.annotations.PasswordMatches;
import com.robert.chatapp.dto.RegisterUserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        RegisterUserDto user = (RegisterUserDto) object;

        return user.getPassword().equals(user.getConfirmPassword());
    }
}
