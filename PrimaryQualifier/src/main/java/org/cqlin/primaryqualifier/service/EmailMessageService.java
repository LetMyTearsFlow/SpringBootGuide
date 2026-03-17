package org.cqlin.primaryqualifier.service;

import org.springframework.stereotype.Service;

@Service
public class EmailMessageService implements MessageService{
    @Override
    public String send() {
        return "Message from Email service";
    }
}
