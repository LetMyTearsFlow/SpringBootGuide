package org.cqlin.dynamicsqldemo;

import org.apache.ibatis.session.SqlSessionFactory;
import org.cqlin.dynamicsqldemo.mapper.DynamicSqlMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
class DynamicSqlDemoApplicationTests {
    @Autowired private JdbcTemplate jdbc;
    @Autowired private SqlSessionFactory factory;

    /** 验证内存数据库、初始数据和 Mapper 注册，未完成 SQL 也可以通过。 */
    @Test
    void contextLoads() {
        assertEquals(5, jdbc.queryForObject("SELECT COUNT(*) FROM t_employee", Integer.class));
        assertEquals("张三", jdbc.queryForObject("SELECT employee_name FROM t_employee WHERE id=101", String.class));
        assertTrue(factory.getConfiguration().hasMapper(DynamicSqlMapper.class));
    }
}
