package org.cqlin.mybatisbasicdemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.cqlin.mybatisbasicdemo.vo.User;

import java.util.List;

@Mapper
public interface DemoMapper {
    User findUserById(Integer id);

    List<User> findAllUser();

    Integer insertUser(User user);

    void updateUser(User user);

    void deleteUser(Integer id);
}
