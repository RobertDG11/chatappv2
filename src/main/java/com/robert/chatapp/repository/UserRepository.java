package com.robert.chatapp.repository;

import com.robert.chatapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getUserById(Long id);

    User getUserByDateCreated(Date dateCreated);

    User getUserByPhoneNumber(String phoneNumber);

    User getUserByEmailAddress(String emailAddress);

    List<User> getUsersByNotificationType(String notificationType);

    List<User> getUsersByActive(Boolean active);

    @Query("select u from User u inner join UserGroup g " +
            "on u.id=g.user.id where " +
            "g.group.id=:gid")
    List<User> getUsersByGroupId(@Param("gid") Long gid);

    @Query("select u from User u inner join Message m " +
            "on u.id=m.user.id inner join UserGroup g " +
            "on m.group.id=g.group.id " +
            "where m.id=:mid and g.group.id=:gid")
    User getUserByMessageIdAndGroupId(@Param("mid") Long mid, @Param("gid") Long gid);



}
