package org.cqlin.mybatisbasicdemo;

import org.cqlin.mybatisbasicdemo.mapper.SingleParameterMapper;
import org.cqlin.mybatisbasicdemo.vo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class SingleParameterMyBatisTests {

    @Autowired
    SingleParameterMapper singleParameterMapper;

    @Test
    void testAddUser() {
        User user = new User(-1, "shuaijiamo", 67);
        singleParameterMapper.addUser(user);
    }

    @Test
    void testGetTheSecondUser() {
        List<Integer> list = Arrays.asList(0, 1);
        User theSecondUser = singleParameterMapper.getTheSecondUser(list);
        System.out.println(theSecondUser);
    }

    @Test
    void testAddUserMap() {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", "wangchuqin");
        userMap.put("age", 32);
        singleParameterMapper.addUserMap(userMap);
    }
}
