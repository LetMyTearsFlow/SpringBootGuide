package org.cqlin.mybatisbasicdemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.cqlin.mybatisbasicdemo.vo.User;

@Mapper
public interface DemoMapper {
    User findUserById(Integer id);
}
