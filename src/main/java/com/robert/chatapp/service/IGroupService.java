package com.robert.chatapp.service;

import com.robert.chatapp.entity.Group;

public interface IGroupService {

    Group createGroup(String name, Long createdById);
    Group getGroupById(Long id);
    void insertNewUser(Long gid, Long uid);
    void removeUser(Long gid, Long uid);
}
