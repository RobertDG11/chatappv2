package com.robert.chatapp.service;

import com.robert.chatapp.dto.RegisterUserDto;
import com.robert.chatapp.dto.UserDtoConversions;
import com.robert.chatapp.entity.User;
import com.robert.chatapp.entity.VerificationToken;
import com.robert.chatapp.exceptions.EmailAlreadyExistsException;
import com.robert.chatapp.exceptions.InvalidTokenException;
import com.robert.chatapp.exceptions.UserNotFoundException;
import com.robert.chatapp.exceptions.UsernameAlreadyExistsException;
import com.robert.chatapp.repository.TokenRepository;
import com.robert.chatapp.repository.UserRepository;
import com.robert.chatapp.utils.SecureTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private static final String TOKEN_INVALID = "invalidToken";
    private static final String TOKEN_EXPIRED = "expired";
    private static final String TOKEN_VALID = "valid";

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserDtoConversions userDtoConversions;

    BCryptPasswordEncoder bCryptPasswordEncoder;

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

        user.setDateCreated(new Date());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User getUser(Long id) {

        return userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User getUser(Date dateCreated) {

        return userRepository.getUserByDateCreated(dateCreated).
                orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User getUserByEmail(String emailAddress) {

        return userRepository.getUserByEmailAddress(emailAddress).
                orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User getUserByPhone(String phoneNumber) {

        return userRepository.getUserByPhoneNumber(phoneNumber).
                orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User getUserByUsername(String username) {

        return userRepository.getUserByUsername(username).
                orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User getUserByToken(String token) {

        Optional<VerificationToken> verificationToken = tokenRepository.findByConfirmationToken(token);

        return (verificationToken.orElseThrow(() -> new UserNotFoundException("User not found"))).getUserId();
    }

    @Override
    @Transactional
    public User editUser(RegisterUserDto newUser) {

        Optional<User> oldUser = userRepository.findById(newUser.getId());

        oldUser.ifPresent(a -> updateValues(a, userDtoConversions.convertToEntityRegister(newUser)));

        return oldUser.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public User deleteUser(Long id) {

        Optional<User> userToDelete = userRepository.findById(id);

        userToDelete.ifPresent(user -> userRepository.delete(user));

        return userToDelete.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public List<User> getAllUsers(int pageNumber, int pageSize) {

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        return userRepository.findAll(pageRequest).getContent();
    }

    @Override
    public List<User> getAllUsers(String notificationType) {

        return userRepository.getUsersByNotificationType(notificationType);
    }

    @Override
    public List<User> getAllUsers(Boolean active) {

        return userRepository.getUsersByActive(active);
    }

    @Override
    public List<User> getAllUsersInGroup(Long gid) {

        return userRepository.getUsersByGroupId(gid);
    }

    @Override
    public User getUserByMessageInGroup(Long mid, Long gid) {

        return userRepository.getUserByMessageIdAndGroupId(mid, gid).
                orElseThrow(() -> new UserNotFoundException("User not found"));
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
    public VerificationToken generateNewVerificationToken(String existingVerificationToken) {

        Optional<VerificationToken> token = tokenRepository.findByConfirmationToken(existingVerificationToken);

        token.ifPresent(t -> {t.updateToken(SecureTokenGenerator.nextToken());
            tokenRepository.save(t);});

        return token.orElseThrow(() -> new InvalidTokenException("Invalid token"));
    }

    private void updateValues(User oldUser, User newUser) {

        if (userRepository.getUserByUsername(newUser.getUsername()).isPresent()) {

            throw new UsernameAlreadyExistsException("There is an user with the same username. " +
                    "Please choose another one!");
        }

        if (userRepository.getUserByEmailAddress(newUser.getEmailAddress()).isPresent()) {

            throw new EmailAlreadyExistsException("Email already exists");
        }

        oldUser.setFirstName(newUser.getFirstName());
        oldUser.setLastName(newUser.getLastName());
        oldUser.setUsername(newUser.getUsername());
        oldUser.setEmailAddress(newUser.getEmailAddress());
        oldUser.setPhoneNumber(newUser.getPhoneNumber());
        oldUser.setPassword(newUser.getPassword());
    }
}
