package org.cqlin.mybatisbasicdemo;

import org.cqlin.mybatisbasicdemo.mapper.DemoMapper;
import org.cqlin.mybatisbasicdemo.vo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MyBatisBasicDemoApplicationTests {

    @Autowired
    private DemoMapper demoMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void testFindUserById() {
        User user = demoMapper.findUserById(1);
        System.out.println(user);
    }

    @Test
    public void testFindAllUsers() {
        List<User> userList = demoMapper.findAllUser();
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void testUpdateUser() {
        User user = new User(1,"zhangsan", 38);
        demoMapper.updateUser(user);
    }

    @Test
    public void testInsertUser() {
        User user = new User(-1, "lisi",48);
        demoMapper.insertUser(user);
        System.out.println(user.getId());
    }

    @Test
    public void testDeleteUser() {
        demoMapper.deleteUser(2);
    }

}
