package org.cqlin.injectiondemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final MessageService messageService;

    private MessageService anotherMessageService;

    /**
     * 构造器注入
     */
    public UserService(@Qualifier("emailMessageService")MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Setter 方法注入
     */
    @Autowired
    public void setAnotherMessageService(@Qualifier("smsMessageService")MessageService anotherMessageService) {
        this.anotherMessageService = anotherMessageService;
    }

    public String registerUser(String name) {
        return "构造器注入 -> " + messageService.sendMessage(name);
    }

    public String notifyUser(String name) {
        return "Setter注入 -> " + anotherMessageService.sendMessage(name);
    }

}
