package org.cqlin.resourceautowired.service;

import org.springframework.stereotype.Service;

@Service("userServiceImpl1")
public class UserServiceImpl1 implements UserService {
    @Override
    public String getUser() {
        return "User from impl1";
    }
}
