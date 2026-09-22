package org.cqlin.mybatisreturndemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.apache.ibatis.session.SqlSessionFactory;
import org.cqlin.mybatisreturndemo.mapper.UserReturnMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MyBatisReturnDemoApplicationTests {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    // 只检查练习环境和种子数据，不要求完成任何 SQL 待办。
    @Test
    void contextLoads() {
        assertEquals(4, jdbcTemplate.queryForObject("SELECT COUNT(*) FROM t_user", Integer.class));
        assertEquals("张三", jdbcTemplate.queryForObject("SELECT user_name FROM t_user WHERE id = 1", String.class));
        assertTrue(sqlSessionFactory.getConfiguration().hasMapper(UserReturnMapper.class));
    }
}
