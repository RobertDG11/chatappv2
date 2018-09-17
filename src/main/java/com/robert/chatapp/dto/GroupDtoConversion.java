package com.robert.chatapp.dto;

import com.robert.chatapp.entity.Group;
import com.robert.chatapp.service.GroupService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupDtoConversion {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GroupService groupService;

    public GroupDto convertToDto(Group group) {

        return modelMapper.map(group, GroupDto.class);

    }

    public Group convertToEntity(GroupDto groupDto) {

        Group group = modelMapper.map(groupDto, Group.class);

        if (group.getId() != null) {
            Group oldGroup = groupService.getGroupById(groupDto.getId());
        }

        return group;
    }
}
