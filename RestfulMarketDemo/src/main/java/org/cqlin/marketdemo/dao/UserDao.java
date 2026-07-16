package org.cqlin.marketdemo.dao;

import org.cqlin.marketdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setPhone(rs.getString("phone"));
        user.setCreate_time(rs.getTimestamp("create_time").toLocalDateTime());
        return user;
    };

    public void createUser(User user) {
        String sql = "insert into user(username, password, phone) values(?,?,?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getPhone());
    }

    public User findUserById(Long id) {
        String sql = "select * from user where id = ?";
        List<User> query = jdbcTemplate.query(sql, userRowMapper, id);
        return query.get(0);
    }

    public void updateUser(User user) {
        Long id = user.getId();
        User oldUser = findUserById(id);
        if (oldUser == null) {
            throw new RuntimeException("用户不存在");
        }
        if (user.getUsername() != null) {
            oldUser.setUsername(user.getUsername());
        }
        if (user.getPassword() != null) {
            oldUser.setPassword(user.getPassword());
        }
        if (user.getPhone() != null) {
            oldUser.setPhone(user.getPhone());
        }
        String sql = "update user set username=?, password=?, phone=? where id=?";
        jdbcTemplate.update(sql, oldUser.getUsername(), oldUser.getPassword(), oldUser.getPhone(), oldUser.getId());
    }

    public void deleteUserById(Long id) {
        String sql = "delete from user where id=?";
        jdbcTemplate.update(sql, id);
    }

    public List<User> findAllUsers() {
        String sql = "select * from user";
        return jdbcTemplate.query(sql, userRowMapper);
    }
}
