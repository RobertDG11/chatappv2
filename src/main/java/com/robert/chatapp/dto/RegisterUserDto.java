package com.robert.chatapp.dto;

import com.robert.chatapp.annotations.PasswordMatches;
import com.robert.chatapp.annotations.ValidEmail;
import com.robert.chatapp.annotations.ValidPassword;
import com.robert.chatapp.annotations.ValidPhoneNumber;
import org.springframework.context.annotation.Bean;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@PasswordMatches
public class RegisterUserDto {

    private Long id;

    @NotNull(message = "This field can't be null")
    @NotEmpty(message = "This field can't be empty")
    private String firstName;

    @NotNull(message = "This field can't be null")
    @NotEmpty(message = "This field can't be empty")
    private String lastName;

    @NotNull(message = "This field can't be null")
    @NotEmpty(message = "This field can't be empty")
    private String username;

    @NotNull(message = "This field can't be null")
    @NotEmpty(message = "This field can't be empty")
    @ValidEmail
    private String emailAddress;

    @NotNull(message = "This field can't be null")
    @NotEmpty(message = "This field can't be empty")
    @ValidPhoneNumber
    private String phoneNumber;

    @ValidPassword
    private String password;

    private String confirmPassword;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RegisterUserDto{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
