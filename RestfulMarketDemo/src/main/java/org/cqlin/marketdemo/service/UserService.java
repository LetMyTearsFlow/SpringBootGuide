package org.cqlin.marketdemo.service;

import org.cqlin.marketdemo.common.Result;
import org.cqlin.marketdemo.dao.UserDao;
import org.cqlin.marketdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public void register(User user) {
        userDao.createUser(user);
    }

    public User getUser(Long id) {
        return userDao.findUserById(id);
    }
}
