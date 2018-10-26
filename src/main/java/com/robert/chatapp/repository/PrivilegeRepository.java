package com.robert.chatapp.repository;

import com.robert.chatapp.entity.Group;
import com.robert.chatapp.entity.Privilege;
import com.robert.chatapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    @Query("select p from Privilege p " +
            "inner join p.userTypes t " +
            "inner join UserGroup u on t.id=u.userType.id "+
            "where u.user=:user and u.group=:gr")
    List<Privilege> getPrivilegesOfUser(@Param("user") User user, @Param("gr") Group group);

}
