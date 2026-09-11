package org.cqlin.mybatisbasicdemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cqlin.mybatisbasicdemo.vo.User;

import java.util.List;
import java.util.Map;

@Mapper
public interface ParameterMapper {
    User getUser(Integer id);

    User getTheSecondUser(List<Integer> id);

    void addUser(User user);

    void addUserMap(Map<String, Object> userMap);

    User getUserWithMultipleParameters(@Param("id") Integer id, @Param("info") Map<String, Object> userInfo);
}
