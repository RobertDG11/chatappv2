package com.robert.chatapp.repository;

import com.robert.chatapp.entity.Group;
import com.robert.chatapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u")
    List<User> getAllUsers();

    User getUserById(int id);

    User getUserByDateCreated(Date dateCreated);

    User getUserByPhoneNumber(String phoneNumber);

    User getUserByEmailAddress(String emailAddress);

    List<User> getUsersByNotificationType(String notificationType);

//    List<User> getUsersByActive(boolean isActive);

    @Query("select u from User u inner join UserGroup g " +
            "on u.id=g.user.id where " +
            "g.id.groupId=:id")
    List<User> getUsersByGroupId(@Param("id") Long id);

}
