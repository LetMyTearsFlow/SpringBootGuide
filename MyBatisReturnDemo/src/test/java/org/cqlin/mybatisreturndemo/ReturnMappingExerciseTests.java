package org.cqlin.mybatisreturndemo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.cqlin.mybatisreturndemo.mapper.UserReturnMapper;
import org.cqlin.mybatisreturndemo.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnabledIfSystemProperty(named = "exercise", matches = "true")
class ReturnMappingExerciseTests {
    @Autowired
    private UserReturnMapper mapper;

    // 验收 1：总数以单值返回。
    @Test
    void step1_count() {
        assertEquals(4L, mapper.countUsers());
    }

    // 验收 2：对象属性映射完整，查不到时返回 null。
    @Test
    void step2_object() {
        assertUser(mapper.findById(1L), 1L, "张三", 20);
        assertUser(mapper.findById(2L), 2L, "李四", 25);
        assertNull(mapper.findById(999L));
    }

    // 验收 3：筛选与排序正确，无匹配行时返回空列表。
    @Test
    void step3_list() {
        List<User> users = mapper.findByMinAge(25);
        assertEquals(2, users.size());
        assertUser(users.get(0), 2L, "李四", 25);
        assertUser(users.get(1), 3L, "王五", 25);
        assertEquals(3, mapper.findByMinAge(20).size());
        assertTrue(mapper.findByMinAge(100).isEmpty());
    }

    // 验收 4：单行 Map 的键是数据库列名，值是对应列值。
    @Test
    void step4_rowMap() {
        Map<String, Object> row = mapper.findRow(1L);
        assertNotNull(row);
        assertEquals(Set.of("id", "user_name", "age"), row.keySet());
        assertEquals(1L, ((Number) row.get("id")).longValue());
        assertEquals("张三", row.get("user_name"));
        assertEquals(20, ((Number) row.get("age")).intValue());
        assertNull(mapper.findRow(999L));
    }

    // 验收 5：外层键是 Long 类型的主键，值是完整的 User。
    @Test
    void step5_keyedMap() {
        Map<Long, User> users = mapper.findAllAsMap();
        assertEquals(Set.of(1L, 2L, 3L, 4L), users.keySet());
        assertUser(users.get(1L), 1L, "张三", 20);
        assertUser(users.get(2L), 2L, "李四", 25);
        assertUser(users.get(3L), 3L, "王五", 25);
        assertUser(users.get(4L), 4L, "赵六", null);
    }

    // 验收 6：关闭驼峰映射后，仍正确封装下划线列名和空年龄。
    @Test
    void step6_resultMap() {
        assertUser(mapper.findWithResultMap(3L), 3L, "王五", 25);
        assertUser(mapper.findWithResultMap(4L), 4L, "赵六", null);
        assertNull(mapper.findWithResultMap(999L));
    }

    // 验收 7：普通值、SQL NULL、没有记录三种情况。
    @Test
    void step7_nullableValue() {
        assertEquals(Integer.valueOf(20), mapper.findAge(1L));
        assertNull(mapper.findAge(4L));
        assertNull(mapper.findAge(999L));
    }

    // 统一检查对象的全部属性，让漏映射字段能被测试发现。
    private static void assertUser(User user, Long id, String name, Integer age) {
        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals(name, user.getUserName());
        assertEquals(age, user.getAge());
    }
}
