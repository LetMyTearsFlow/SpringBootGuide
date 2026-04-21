package org.cqlin.jdbctemplatedemo;

import org.cqlin.jdbctemplatedemo.dao.BookDao;
import org.cqlin.jdbctemplatedemo.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class JdbcTemplateDemoApplicationTests {

    @Autowired
    BookDao bookDao;

    @Test
    void testBookDaoInsert() {
        Book book = new Book("安娜·卡列尼娜", "列夫·托尔斯泰", BigDecimal.valueOf(50), 10, 0L, 0);
        bookDao.insert(book);
    }

    @Test
    void testBookDaoFindById() {
        int id = 1;
        Book book = bookDao.findById(id);
        System.out.println(book);
    }

    @Test
    void testBookDaoFindAll() {
        List<Book> books = bookDao.findAll();
        for(Book book : books) {
            System.out.println(book);
        }
    }

    @Test
    void testBookDaoUpdate() {
        Book book = bookDao.findById(1);
        book.setStock(book.getStock() + 1);
        bookDao.update(book);
    }

}
