package org.cqlin.jdbctemplatedemo.dao;

import org.cqlin.jdbctemplatedemo.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryDao {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public void insert(Category category) {
        String sql = "insert into category(name, description) values(?,?)";
        jdbcTemplate.update(sql, category.getName(), category.getDescription());
    }

    public Category findById(int id) {
        return null;
    }

    public List<Category> findAll() {
        return null;
    }

    public void update(Category category) {

    }

    public void deleteById(int id) {

    }
}
