package com.robert.chatapp.registration;

import com.robert.chatapp.entity.User;
import com.robert.chatapp.exceptions.InvalidTokenException;
import com.robert.chatapp.exceptions.UserAlreadyActivatedException;
import com.robert.chatapp.service.IRegisterService;
import com.robert.chatapp.service.UserService;
import com.robert.chatapp.utils.SecureTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;



@Component
public class RegisterListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private IRegisterService service;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {

        this.confirmRegistration(onRegistrationCompleteEvent);
    }

    private void confirmRegistration(final OnRegistrationCompleteEvent event) {

        final User user = event.getUser();
        final String token;

        if (user.getVerificationToken() == null) {
            token = SecureTokenGenerator.nextToken();
            service.createVerificationTokenForUser(user, token);
        }
        else {
            token = user.getVerificationToken().getConfirmationToken();
        }

        final SimpleMailMessage email = constructEmailMessage(event, user, token);
        mailSender.send(email);
    }

    private SimpleMailMessage constructEmailMessage(OnRegistrationCompleteEvent event,
                                                    User user, String token) {

        String recipientAddress = user.getEmailAddress();
        String subject = "Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/api/register/confirm?token=" + token;
        String message = "Account successfully created";
        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(env.getProperty("support.email"));

        return email;
    }
}
