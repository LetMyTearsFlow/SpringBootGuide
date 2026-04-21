package org.cqlin.jdbctemplatedemo.dao;

import org.cqlin.jdbctemplatedemo.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final RowMapper<Book> bookRowMapper = (rs, rowNum) -> {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setPrice(rs.getBigDecimal("price"));
        book.setStock((int) rs.getLong("stock"));
        book.setStatus(rs.getInt("status"));
        book.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        book.setCategoryId(rs.getLong("category_id"));
        return book;
    };

    public void insert(Book book) {
        String sql = "insert into book(title, author, price, stock, category_id, status) values(?,?,?,?,?,?)";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPrice(),
                book.getStock(), book.getCategoryId(), book.getStatus());
    }

    public Book findById(int id) {
        String sql = "select * from book where id=?";
        List<Book> query = jdbcTemplate.query(sql, bookRowMapper, id);
        return query.get(0);
    }

    /**
     * find all book from book database
     *
     * @return all books from book database
     */
    public List<Book> findAll() {
        String sql = "select * from book";
        return jdbcTemplate.query(sql, bookRowMapper);
    }

    public void update(Book book) {
        String sql = "update book set title=?, author=?, price=?, stock=?, category_id=?, " +
                "status=?, created_at=? where id=?";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPrice(), book.getStock(),
                book.getCategoryId(), book.getStatus(), book.getCreatedAt(), book.getId());
    }

}
