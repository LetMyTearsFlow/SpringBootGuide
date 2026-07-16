package org.cqlin.marketdemo.dao;

import org.cqlin.marketdemo.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserDaoTest {

    @Autowired
    public UserDao userDao;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("user0722");
        user.setPhone("1145141919810");
        user.setPassword("passw0de");
        userDao.createUser(user);
    }

    @Test
    public void testFindUserById() {
        User user = userDao.findUserById(1L);
        System.out.println("username: " + user.username);
        Assertions.assertThrows(RuntimeException.class, () -> userDao.findUserById(1008L));
    }

    @Test
    void updateUser() {
        User user = new User();
        user.setPassword("123456");
        user.setId(1L);
        userDao.updateUser(user);
    }

    @Test
    void testFindAllUsers() {
        List<User> allUsers = userDao.findAllUsers();
        for (User user : allUsers) {
            System.out.println(user.getUsername());
        }
    }
}
