package org.cqlin.injectiondemo.service;

import org.springframework.stereotype.Service;

@Service
public class SmsMessageService implements MessageService {
    @Override
    public String sendMessage(String name) {
        return "SMS消息：欢迎你，" + name;
    }
}
