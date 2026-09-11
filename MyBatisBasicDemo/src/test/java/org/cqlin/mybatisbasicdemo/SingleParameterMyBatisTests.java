package org.cqlin.mybatisbasicdemo;

import org.cqlin.mybatisbasicdemo.mapper.ParameterMapper;
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
    ParameterMapper parameterMapper;

    @Test
    void testAddUser() {
        User user = new User(-1, "shuaijiamo", 67);
        parameterMapper.addUser(user);
    }

    @Test
    void testGetTheSecondUser() {
        List<Integer> list = Arrays.asList(0, 1);
        User theSecondUser = parameterMapper.getTheSecondUser(list);
        System.out.println(theSecondUser);
    }

    @Test
    void testAddUserMap() {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", "wangchuqin");
        userMap.put("age", 32);
        parameterMapper.addUserMap(userMap);
    }

    @Test
    void testGetUserWithMultipleParameters() {
        Map<String, Object> userInfoMap = new HashMap<>();
        userInfoMap.put("name", "zhangsan");
        userInfoMap.put("age", 38);
        parameterMapper.getUserWithMultipleParameters(1, userInfoMap);
    }
}
