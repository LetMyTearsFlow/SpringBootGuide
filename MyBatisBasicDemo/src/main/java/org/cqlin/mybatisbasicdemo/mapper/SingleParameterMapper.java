package org.cqlin.mybatisbasicdemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.cqlin.mybatisbasicdemo.vo.User;

import java.util.List;
import java.util.Map;

@Mapper
public interface SingleParameterMapper {
    User getUser(Integer id);

    User getTheSecondUser(List<Integer> id);

    void addUser(User user);

    void addUserMap(Map<String, Object> userMap);
}
