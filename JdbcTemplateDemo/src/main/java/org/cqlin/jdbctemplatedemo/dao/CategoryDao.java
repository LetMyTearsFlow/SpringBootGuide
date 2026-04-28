package org.cqlin.jdbctemplatedemo.dao;

import org.cqlin.jdbctemplatedemo.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryDao {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    private final RowMapper<Category> categoryRowMapper = (rs, rowNum) -> {
        Category category = new Category();
        category.setId(rs.getLong("id"));
        category.setName(rs.getString("name"));
        category.setDescription(rs.getString("description"));
        category.setCreateTime(rs.getTimestamp("created_at").toLocalDateTime());
        return category;
    };

    public void insert(Category category) {
        String sql = "insert into category(name, description) values(?,?)";
        jdbcTemplate.update(sql, category.getName(), category.getDescription());
    }

    public Category findById(int id) {
        String sql = "select * from category where id=?";
        try {
            return jdbcTemplate.query(sql, categoryRowMapper, id).get(0); // 项目用JDK 17的不能用getFirst
        } catch (IndexOutOfBoundsException exception) {
            System.out.println(exception);
            return null;
        }
    }

    public List<Category> findAll() {
        String sql = "select * from category";
        return jdbcTemplate.query(sql, categoryRowMapper);
    }

    public void update(Category category) {
        String sql = "update category set name=?, description=?, created_at=? where id=?";
        jdbcTemplate.update(sql, category.getName(), category.getDescription(), category.getCreateTime(), category.getId());
    }

    public void deleteById(int id) {

    }
}
