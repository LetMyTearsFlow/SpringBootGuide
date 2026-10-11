package org.cqlin.mybatisjoindemo;

import org.apache.ibatis.session.SqlSessionFactory;
import org.cqlin.mybatisjoindemo.mapper.JoinExerciseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
// 测试只需依赖注入，不启用会触发 Java 代理挂载的 Mockito 监听器。
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
class EnvironmentTests {
    @Autowired private JdbcTemplate jdbc;
    @Autowired private SqlSessionFactory factory;

    // 验证三张表、种子数据及 Mapper 注册，不依赖练习答案。
    @Test
    void contextLoads() {
        assertEquals(3, jdbc.queryForObject("SELECT COUNT(*) FROM t_customer", Integer.class));
        assertEquals(3, jdbc.queryForObject("SELECT COUNT(*) FROM t_order", Integer.class));
        assertEquals(3, jdbc.queryForObject("SELECT COUNT(*) FROM t_order_item", Integer.class));
        assertEquals("张三", jdbc.queryForObject("SELECT customer_name FROM t_customer WHERE id=1", String.class));
        assertTrue(factory.getConfiguration().hasMapper(JoinExerciseMapper.class));
    }
}
