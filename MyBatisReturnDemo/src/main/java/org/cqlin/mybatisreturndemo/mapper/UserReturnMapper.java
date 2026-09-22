package org.cqlin.mybatisreturndemo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cqlin.mybatisreturndemo.model.User;

@Mapper
public interface UserReturnMapper {
    // 练习 1：返回用户总数。
    long countUsers();

    // 练习 2：按主键返回对象，使用 SQL 别名映射 userName。
    User findById(@Param("id") Long id);

    // 练习 3：按年龄下限查询列表，按 id 升序。
    List<User> findByMinAge(@Param("minAge") Integer minAge);

    // 练习 4：把一行转成 Map，保留数据库列名作为键。
    Map<String, Object> findRow(@Param("id") Long id);

    // 练习 5：多行转成以用户 id 为键的 Map；TODO：自行补充所需注解。
    @MapKey("id")
    Map<Long, User> findAllAsMap();

    // 练习 6：使用 resultMap 映射对象，不使用 SQL 别名。
    User findWithResultMap(@Param("id") Long id);

    // 练习 7：返回可空单值，观察 SQL NULL 和无行时的返回结果。
    Integer findAge(@Param("id") Long id);
}
