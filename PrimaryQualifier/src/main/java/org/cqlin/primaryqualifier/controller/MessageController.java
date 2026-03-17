package org.cqlin.primaryqualifier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.cqlin.primaryqualifier.service.MessageService;

@Controller
public class MessageController {

    @Qualifier("emailMessageService") // 如果有这行，注入的就是emailMessageService;否则就是mySqlMessageService
    @Autowired
    private MessageService messageService;

    public void send() {
        System.out.println(messageService.send());
    }
}
