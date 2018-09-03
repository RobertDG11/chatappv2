package com.robert.chatapp.repository;

import com.robert.chatapp.entity.User;
import com.robert.chatapp.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByConfirmationToken(String token);

    Optional<VerificationToken> findByUserId(User user);
}
