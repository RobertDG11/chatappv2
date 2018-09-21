package com.robert.chatapp.service;

import com.robert.chatapp.entity.Message;

import java.util.List;

public interface IMessageService {

    Message getMessageById(Long mid);
    void writeMessage(String body, Long uid, Long gid);
    void deleteAllMessages(Long gid, Long uid);
    List<Message> getMessageByUserAndGroup(Long gid, Long uid);
    void deleteMessage(Long mid);
}
