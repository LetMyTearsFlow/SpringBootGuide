package org.cqlin.primaryqualifier.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class MySqlMessageService implements MessageService{
    @Override
    public String send() {
        return "Message from MySql service";
    }
}
