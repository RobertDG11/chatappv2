package com.robert.chatapp.service;

import com.robert.chatapp.dto.GroupDto;
import com.robert.chatapp.dto.GroupDtoConversion;
import com.robert.chatapp.entity.Group;
import com.robert.chatapp.entity.User;
import com.robert.chatapp.entity.UserType;
import com.robert.chatapp.exceptions.GroupNotFoundException;
import com.robert.chatapp.exceptions.UserNotActivatedException;
import com.robert.chatapp.repository.GroupRepository;
import com.robert.chatapp.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService implements IGroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    IUserService userService;

    @Autowired
    UserTypeRepository userTypeRepository;

    @Autowired
    GroupDtoConversion groupDtoConversion;

    @Override
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public Group createGroup(String name, Long createdById) {

        Group group = new Group();

        group.setName(name);
        group.setDateCreated(new Date());

        User createdBy = userService.getUser(createdById);

        if (!createdBy.isActive()) {

            throw new UserNotActivatedException("This account is not activated. Please check your email and confirm" +
                    " your account");
        }

        UserType userType = userTypeRepository.getOne(1);

        group = groupRepository.save(group);

        createdBy.addGroup(group, userType);

        createdBy.getCreatedGroups().add(group);

        group.setCreatedBy(createdBy);

        return group;

    }

    @Override
    public Group getGroupById(Long id) {

        return groupRepository.findById(id).orElseThrow(() -> new GroupNotFoundException("Group not found"));
    }

    @Override
    public boolean insertNewUser(Long gid, Long uid) {

        User user = userService.getUser(uid);
        Group group = getGroupById(gid);

        if (user.isInGroup(group)) {
            return false;
        }

        UserType userType = userTypeRepository.getOne(3);

        user.addGroup(group, userType);

        groupRepository.save(group);

        return true;

    }

    @Override
    @Transactional
    public void removeUser(Long gid, Long uid) {

        User user = userService.getUser(uid);
        Group group = getGroupById(gid);

        user.removeGroup(group);

    }

    @Override
    @Transactional
    public void deleteGroup(Long gid) {

        Group group = getGroupById(gid);

        group.getCreatedBy().getCreatedGroups().remove(group);

        groupRepository.delete(group);
    }

    @Override
    public List<GroupDto> getAllGroups() {

        return groupRepository.findAll().stream()
                .map(group -> groupDtoConversion.convertToDto(group).setNumberOfUsers(group.getUsers().size()))
                .collect(Collectors.toList());

    }

    @Override
    public void blockUser(Long gid, Long uid) {

        User user = userService.getUser(uid);
        Group group = getGroupById(gid);

        user.blockUser(group);

        groupRepository.save(group);
    }

    @Override
    public Group createPrivateConversation(Long createdById, Long otherUser) {

        Group group = createGroup("Private conversation", createdById);

        insertNewUser(group.getId(), otherUser);

        return group;
    }


}
