package com.robert.chatapp.service;

import com.robert.chatapp.dto.GroupDto;
import com.robert.chatapp.entity.Group;

import java.util.List;

public interface IGroupService {

    Group createGroup(String name, Long createdById);
    Group getGroupById(Long id);
    void insertNewUser(Long gid, Long uid);
    void removeUser(Long gid, Long uid);
    void deleteGroup(Long gid);
    List<GroupDto> getAllGroups();
    void blockUser(Long gid, Long uid);
    Group createPrivateConversation(Long createdById, Long otherUser);
}
