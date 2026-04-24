package org.cqlin.jdbctemplatedemo.dao;

import org.cqlin.jdbctemplatedemo.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
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

    public int insert(Book book) {
        String sql = "insert into book(title, author, price, stock, category_id, status) values(?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setBigDecimal(3, book.getPrice());
            ps.setInt(4, book.getStock());
            ps.setInt(5, Math.toIntExact(book.getCategoryId()));
            ps.setString(6, String.valueOf(book.getStatus()));

            return ps;
        },keyHolder);
        return keyHolder.getKey().intValue();
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

    public void deleteById(int id) {
        String sql = "delete from book where id=?";
        jdbcTemplate.update(sql, id);
    }

}
