package org.cqlin.injectiondemo.service;

import org.springframework.stereotype.Service;

@Service
public class EmailMessageService implements MessageService {
    @Override
    public String sendMessage(String name) {
        return "Email消息：欢迎你，" + name;
    }
}
