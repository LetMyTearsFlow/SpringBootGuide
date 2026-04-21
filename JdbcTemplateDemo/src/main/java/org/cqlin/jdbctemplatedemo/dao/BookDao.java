package org.cqlin.jdbctemplatedemo.dao;

import org.cqlin.jdbctemplatedemo.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void insert(Book book) {
        String sql = "insert into book(title, author, price, stock, category_id, status) values(?,?,?,?,?,?)";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPrice(),
                book.getStock(), book.getCategoryId(), book.getStatus());
    }

}
