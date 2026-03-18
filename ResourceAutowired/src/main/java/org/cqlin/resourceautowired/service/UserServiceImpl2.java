package org.cqlin.resourceautowired.service;

import org.springframework.stereotype.Service;

@Service("userServiceImpl2")
public class UserServiceImpl2 implements UserService {
    @Override
    public String getUser() {
        return "User from impl2";
    }
}
