package com.robert.chatapp.controller;

import com.robert.chatapp.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    IMessageService messageService;

    @PostMapping("/write")
    ResponseEntity<Object> writeMessage(@RequestBody String body,
                                        @RequestParam Long uid,
                                        @RequestParam Long gid) {

        messageService.writeMessage(body, uid, gid);

        return ResponseEntity.ok("Message successfully sent!");
    }

    @DeleteMapping("/deleteAll")
    ResponseEntity<Object> deleteAllMessages(@RequestParam Long gid, @RequestParam Long uid) {

        messageService.deleteAllMessages(gid, uid);

        return ResponseEntity.ok("Messages successfully deleted!");
    }

    @DeleteMapping("/delete/{mid}")
    ResponseEntity<Object> deleteMessage(@PathVariable Long mid) {

        messageService.deleteMessage(mid);

        return ResponseEntity.ok("Message successfully deleted!");
    }
}
