package org.cqlin.jdbctemplatedemo;

import org.cqlin.jdbctemplatedemo.dao.BookDao;
import org.cqlin.jdbctemplatedemo.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
class JdbcTemplateDemoApplicationTests {

    @Autowired
    BookDao bookDao;

    @Test
    void testBookDaoInsert() {
        Book book = new Book("安娜·卡列尼娜", "列夫·托尔斯泰", BigDecimal.valueOf(50), 10, 0L, 0);
        bookDao.insert(book);
    }

}
