package org.cqlin.marketdemo.dao;

import org.cqlin.marketdemo.entity.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.math.BigDecimal;


@SpringBootTest
class OrderDaoTest {

    @Autowired
    OrderDao orderDao;

    @Test
    void insertOrder() {
        Order order = new Order();
        order.setGoodsId(1L);
        order.setPrice(BigDecimal.valueOf(2000L));
        order.setBuyerId(2L);
        order.setStatus(1);
        orderDao.insertOrder(order);
    }

    @Test
    void findOrderById() {
        Long id = 1L;
        Order order = orderDao.findOrderById(id);
        System.out.println(order.getId());
        Assertions.assertThrows(RuntimeException.class, () -> orderDao.findOrderById(10008L));
    }

    @Test
    void updateOrder() {
        Order order = orderDao.findOrderById(1L);
        order.setPrice(order.getPrice().add(BigDecimal.ONE));
        orderDao.updateOrder(order);
    }

    @Test
    void deleteOrderById() {
    }

    @Test
    void findAllOrders() {
    }
}