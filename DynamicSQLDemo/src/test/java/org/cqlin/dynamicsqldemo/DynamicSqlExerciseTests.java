package org.cqlin.dynamicsqldemo;

import java.util.List;
import org.cqlin.dynamicsqldemo.mapper.DynamicSqlMapper;
import org.cqlin.dynamicsqldemo.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
@EnabledIfSystemProperty(named = "exercise", matches = "true")
class DynamicSqlExerciseTests {
    @Autowired private DynamicSqlMapper mapper;
    @Autowired private JdbcTemplate jdbc;

    /** 提取结果编号，同时验证查询顺序。 */
    private List<Long> ids(List<Employee> employees) {
        return employees.stream().map(Employee::getId).toList();
    }

    /** 第1题：覆盖零条件、单条件、组合条件及无结果。 */
    @Test
    void step1_ifWhere() {
        assertEquals(List.of(101L, 102L, 103L, 104L, 105L), ids(mapper.search(null, null, null)));
        assertEquals(5, mapper.search("", null, null).size());
        assertEquals(List.of(101L, 104L), ids(mapper.search("张", null, null)));
        assertEquals(List.of(101L, 102L), ids(mapper.search(null, 1L, null)));
        assertEquals(List.of(102L), ids(mapper.search(null, null, 10000)));
        assertEquals(List.of(104L), ids(mapper.search("张", 2L, 9000)));
        assertTrue(mapper.search("不存在", null, null).isEmpty());
        var employee = mapper.search("张三", null, null).get(0);
        assertEquals("张三", employee.getEmployeeName());
        assertEquals(1L, employee.getDepartmentId());
        assertEquals(8000, employee.getSalary());
        assertEquals("ACTIVE", employee.getStatus());
    }

    /** 第2题：验证分支优先级，而非叠加所有条件。 */
    @Test
    void step2_choose() {
        assertEquals(List.of(101L, 104L), ids(mapper.chooseSearch("张", 1L)));
        assertEquals(List.of(103L, 104L), ids(mapper.chooseSearch("", 2L)));
        assertEquals(List.of(103L, 104L), ids(mapper.chooseSearch(null, 2L)));
        assertEquals(List.of(101L, 102L, 104L), ids(mapper.chooseSearch(null, null)));
        assertTrue(mapper.chooseSearch("不存在", 1L).isEmpty());
    }

    /** 第3题：验证集合遍历、重复值、空集合及不存在的编号。 */
    @Test
    void step3_foreachQuery() {
        assertEquals(List.of(101L, 104L), ids(mapper.findByIds(List.of(104L, 101L, 101L, 999L))));
        assertTrue(mapper.findByIds(List.of()).isEmpty());
        assertTrue(mapper.findByIds(null).isEmpty());
        assertTrue(mapper.findByIds(List.of(999L)).isEmpty());
    }

    /** 第4题：验证部分更新、末尾逗号、零值及空更新，结束后自动回滚。 */
    @Test
    void step4_set() {
        var employee = new Employee();
        employee.setId(101L);
        employee.setEmployeeName("张三丰");
        employee.setSalary(10000);
        assertEquals(1, mapper.updateSelective(employee));
        assertEquals("张三丰", jdbc.queryForObject("SELECT employee_name FROM t_employee WHERE id=101", String.class));
        assertEquals(10000, jdbc.queryForObject("SELECT salary FROM t_employee WHERE id=101", Integer.class));
        assertEquals(1L, jdbc.queryForObject("SELECT department_id FROM t_employee WHERE id=101", Long.class));
        assertEquals("ACTIVE", jdbc.queryForObject("SELECT status FROM t_employee WHERE id=101", String.class));
        var patch = new Employee();
        patch.setId(101L);
        patch.setEmployeeName("");
        assertEquals(0, mapper.updateSelective(patch));
        patch.setSalary(0);
        assertEquals(1, mapper.updateSelective(patch));
        assertEquals(0, jdbc.queryForObject("SELECT salary FROM t_employee WHERE id=101", Integer.class));
        assertEquals("张三丰", jdbc.queryForObject("SELECT employee_name FROM t_employee WHERE id=101", String.class));
        patch.setDepartmentId(2L);
        patch.setStatus("INACTIVE");
        assertEquals(1, mapper.updateSelective(patch));
        assertEquals(2L, jdbc.queryForObject("SELECT department_id FROM t_employee WHERE id=101", Long.class));
        assertEquals("INACTIVE", jdbc.queryForObject("SELECT status FROM t_employee WHERE id=101", String.class));
        patch.setId(999L);
        assertEquals(0, mapper.updateSelective(patch));
        assertEquals(12000, jdbc.queryForObject("SELECT salary FROM t_employee WHERE id=102", Integer.class));
    }

    /** 第5题：验证 trim 可正确处理首个有效条件和无条件情况。 */
    @Test
    void step5_trim() {
        assertEquals(List.of(101L, 102L, 103L, 104L, 105L), ids(mapper.trimSearch(null, null)));
        assertEquals(5, mapper.trimSearch(null, "").size());
        assertEquals(List.of(103L, 104L), ids(mapper.trimSearch(2L, null)));
        assertEquals(List.of(103L, 105L), ids(mapper.trimSearch(null, "INACTIVE")));
        assertEquals(List.of(104L), ids(mapper.trimSearch(2L, "ACTIVE")));
        assertTrue(mapper.trimSearch(999L, "ACTIVE").isEmpty());
    }

    /** 第6题：验证批量插入的行数和字段，结束后自动回滚。 */
    @Test
    void step6_foreachInsert() {
        var first = new Employee();
        first.setId(201L);
        first.setEmployeeName("新员工甲");
        first.setDepartmentId(3L);
        first.setSalary(7000);
        first.setStatus("ACTIVE");
        var second = new Employee();
        second.setId(202L);
        second.setEmployeeName("新员工乙");
        second.setSalary(6000);
        second.setStatus("INACTIVE");
        assertEquals(2, mapper.batchInsert(List.of(first, second)));
        assertEquals(7, jdbc.queryForObject("SELECT COUNT(*) FROM t_employee", Integer.class));
        assertEquals("新员工甲", jdbc.queryForObject("SELECT employee_name FROM t_employee WHERE id=201", String.class));
        assertEquals(3L, jdbc.queryForObject("SELECT department_id FROM t_employee WHERE id=201", Long.class));
        assertEquals(7000, jdbc.queryForObject("SELECT salary FROM t_employee WHERE id=201", Integer.class));
        assertEquals("ACTIVE", jdbc.queryForObject("SELECT status FROM t_employee WHERE id=201", String.class));
        assertEquals("新员工乙", jdbc.queryForObject("SELECT employee_name FROM t_employee WHERE id=202", String.class));
        assertNull(jdbc.queryForObject("SELECT department_id FROM t_employee WHERE id=202", Long.class));
        assertEquals(6000, jdbc.queryForObject("SELECT salary FROM t_employee WHERE id=202", Integer.class));
        assertEquals("INACTIVE", jdbc.queryForObject("SELECT status FROM t_employee WHERE id=202", String.class));
    }

    /** 第7题：验证 bind 的包含匹配、空参数及安全的参数绑定。 */
    @Test
    void step7_bind() {
        assertEquals(List.of(101L, 104L), ids(mapper.bindSearch("张")));
        assertEquals(List.of(104L), ids(mapper.bindSearch("小六")));
        assertEquals(5, mapper.bindSearch(null).size());
        assertEquals(5, mapper.bindSearch("").size());
        assertTrue(mapper.bindSearch("' OR 1=1 --").isEmpty());
    }
}
