package com.robert.chatapp.service;

import com.robert.chatapp.entity.Group;
import com.robert.chatapp.entity.User;
import com.robert.chatapp.entity.UserType;
import com.robert.chatapp.exceptions.GroupNotFoundException;
import com.robert.chatapp.exceptions.UserNotActivatedException;
import com.robert.chatapp.repository.GroupRepository;
import com.robert.chatapp.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class GroupService implements IGroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    IUserService userService;

    @Autowired
    UserTypeRepository userTypeRepository;

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

        group.setCreatedBy(createdBy);

        return group;

    }

    @Override
    public Group getGroupById(Long id) {

        return groupRepository.findById(id).orElseThrow(() -> new GroupNotFoundException("Group not found"));
    }

    @Override
    public void insertNewUser(Long gid, Long uid) {

    }
}
