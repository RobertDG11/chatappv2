package com.robert.chatapp.service;

import com.robert.chatapp.entity.Group;
import com.robert.chatapp.entity.Message;
import com.robert.chatapp.entity.Notification;
import com.robert.chatapp.entity.User;
import com.robert.chatapp.exceptions.MessageNotFoundException;
import com.robert.chatapp.repository.MessageRepository;
import com.robert.chatapp.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class MessageService implements IMessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    IUserService userService;

    @Autowired
    IGroupService groupService;

    @Override
    public Message getMessageById(Long mid) {

        return messageRepository.findById(mid).
                orElseThrow(() -> new MessageNotFoundException("Message not found!"));
    }

    @Override
    public List<Message> getMessageByUserAndGroup(Long gid, Long uid) {

        return messageRepository.findMessages(gid, uid);
    }

    @Override
    public void deleteMessage(Long mid) {

        Message message = getMessageById(mid);

        messageRepository.delete(message);
    }

    @Override
    public void writeMessage(String body, Long uid, Long gid) {

        @Valid
        Message message = new Message(body);

        Notification notification = notificationRepository.getOne(1);
        User user = userService.getUser(uid);
        Group group = groupService.getGroupById(gid);

        message.setNotification(notification);
        message.setUser(user);
        message.setGroup(group);

        user.getMessages().add(message);
        group.getMessages().add(message);

        messageRepository.save(message);
    }

    @Override
    public void deleteAllMessages(Long gid, Long uid) {

        List<Message> messages = getMessageByUserAndGroup(gid, uid);

        messages.forEach(message -> messageRepository.delete(message));

    }

}
