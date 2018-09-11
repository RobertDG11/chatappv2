package com.robert.chatapp.repository;

import com.robert.chatapp.entity.Group;
import com.robert.chatapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findGroupByName(String name);

    Optional<Group> findGroupByDateCreated(Date dateCreated);

    List<Group> findGroupsByCreatedBy(User createdBy);

    List<Group> findGroupsByPrivateG(Boolean privateG);
}
