package org.cqlin.mybatisstepquerydemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
class EnvironmentTests {
    @Autowired private org.springframework.jdbc.core.JdbcTemplate jdbc;
    @Autowired private org.apache.ibatis.session.SqlSessionFactory factory;

    // 验证数据初始化和 Mapper 注册，不要求完成练习。
    @Test
    void contextLoads() {
        assertEquals(4, jdbc.queryForObject("SELECT COUNT(*) FROM t_employee", Integer.class));
        assertEquals(3, jdbc.queryForObject("SELECT COUNT(*) FROM t_department", Integer.class));
        assertEquals("研发部", jdbc.queryForObject("SELECT department_name FROM t_department WHERE id=1", String.class));
        assertTrue(factory.getConfiguration().hasMapper(org.cqlin.mybatisstepquerydemo.mapper.StepQueryMapper.class));
    }
}
