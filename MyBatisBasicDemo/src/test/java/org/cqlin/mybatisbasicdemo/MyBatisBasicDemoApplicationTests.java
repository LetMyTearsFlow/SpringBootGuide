package org.cqlin.mybatisbasicdemo;

import org.cqlin.mybatisbasicdemo.mapper.DemoMapper;
import org.cqlin.mybatisbasicdemo.vo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}
