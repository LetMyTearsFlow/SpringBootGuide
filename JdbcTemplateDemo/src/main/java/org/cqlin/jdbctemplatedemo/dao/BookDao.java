package org.cqlin.jdbctemplatedemo.dao;

import org.cqlin.jdbctemplatedemo.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void insert(Book book) {
        String sql = "insert into book(title, author, price, stock, category_id, status) values(?,?,?,?,?,?)";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPrice(),
                book.getStock(), book.getCategoryId(), book.getStatus());
    }

    public Book findById(int id) {
        String sql = "select * from book where id=?";
        List<Book> query = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Book book = new Book();
            book.setId(rs.getLong("id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setPrice(rs.getBigDecimal("price"));
            book.setStock((int) rs.getLong("stock"));
            book.setStatus(rs.getInt("status"));
            book.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return book;
        }, id);
        return query.get(0);
    }

}
