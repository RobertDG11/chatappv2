package com.robert.chatapp.repository;

import com.robert.chatapp.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Optional<Message> findMessageByCreateDate(Date date);

    @Query("select m from Message m " +
            "where m.group.id=:gid and " +
            "m.user.id=:uid")
    List<Message> findMessages(@Param("gid") Long gid, @Param("uid") Long uid);
}
