package org.cqlin.jdbctemplatedemo;

import org.cqlin.jdbctemplatedemo.dao.BookDao;
import org.cqlin.jdbctemplatedemo.dao.CategoryDao;
import org.cqlin.jdbctemplatedemo.entity.Book;
import org.cqlin.jdbctemplatedemo.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JdbcTemplateDemoApplicationTests {

    @Autowired
    BookDao bookDao;

    @Autowired
    CategoryDao categoryDao;

    @Test
    void testBookDaoInsert() {
        Book book = new Book("安娜·卡列尼娜", "列夫·托尔斯泰", BigDecimal.valueOf(50), 10, 0L, 0);
        int id = bookDao.insert(book);
        System.out.println("插入的书本id为： " + id);
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

    @Test
    void testBookDaoDeleteById() {
        Book book = new Book("我是哈基米","夏目漱石",BigDecimal.valueOf(30.21),20,2L,1);
        int id = bookDao.insert(book);
        System.out.println("插入id为" + id + "的书");
        bookDao.deleteById(id);
        System.out.println("删除id为" + id + "的书");
    }

    @Test
    void testCategoryDaoInsert() {
        Category category = new Category("React", "React技术相关图书");
        categoryDao.insert(category);
    }

    @Test
    void testCategoryDaoFindById() {
        int id = 1;
        Category query = categoryDao.findById(1);
        System.out.println(query);
    }

    @Test
    void testCategoryDaoFindById2() {
        int id = 114514; // id that not exist
        assertNull(categoryDao.findById(id));
    }

    @Test
    void testCategoryDaoFindAll() {
        List<Category> all = categoryDao.findAll();
        for (Category category : all) {
            System.out.println(category);
        }
    }
}
