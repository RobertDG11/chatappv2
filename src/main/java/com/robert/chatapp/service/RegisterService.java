package com.robert.chatapp.service;

import com.robert.chatapp.dto.RegisterUserDto;
import com.robert.chatapp.dto.UserDtoConversions;
import com.robert.chatapp.entity.User;
import com.robert.chatapp.entity.VerificationToken;
import com.robert.chatapp.exceptions.EmailAlreadyExistsException;
import com.robert.chatapp.exceptions.InvalidTokenException;
import com.robert.chatapp.exceptions.UserAlreadyActivatedException;
import com.robert.chatapp.exceptions.UsernameAlreadyExistsException;
import com.robert.chatapp.repository.TokenRepository;
import com.robert.chatapp.repository.UserRepository;
import com.robert.chatapp.utils.SecureTokenGenerator;
import org.apache.catalina.session.JDBCStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class RegisterService implements IRegisterService {

    private static final String TOKEN_INVALID = "invalidToken";
    private static final String TOKEN_EXPIRED = "expired";
    private static final String TOKEN_VALID = "valid";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDtoConversions userDtoConversions;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public User createUser(RegisterUserDto newUser) {

        if (userRepository.getUserByUsername(newUser.getUsername()).isPresent()) {

            throw new UsernameAlreadyExistsException("There is an user with the same username. " +
                    "Please choose another one!");
        }

        if (userRepository.getUserByEmailAddress(newUser.getEmailAddress()).isPresent()) {

            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = userDtoConversions.convertToEntityRegister(newUser);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setDateCreated(new Date());

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void createVerificationTokenForUser(final User user, final String token) {

        final VerificationToken myToken = new VerificationToken(token, user);

        tokenRepository.save(myToken);

    }

    @Override
    @Transactional
    public String validateVerificationToken(String token) {

        Optional<VerificationToken> verificationToken = tokenRepository.findByConfirmationToken(token);

        if (!verificationToken.isPresent()) {
            return TOKEN_INVALID;
        }

        User user = verificationToken.get().getUserId();

        final Calendar cal = Calendar.getInstance();

        if ((verificationToken.get().getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {

            tokenRepository.delete(verificationToken.get());
            user.setVerificationToken(null);
            return TOKEN_EXPIRED;
        }

        tokenRepository.delete(verificationToken.get());

        user.setActive(true);
        user.setVerificationToken(null);

        userRepository.save(user);


        return TOKEN_VALID;
    }

    @Override
    public void generateNewVerificationToken(User user) {

        Optional<VerificationToken> token = tokenRepository.findByUserId(user);

        token.ifPresent(t -> {t.updateToken(SecureTokenGenerator.nextToken());
            tokenRepository.save(t);});

    }
}
