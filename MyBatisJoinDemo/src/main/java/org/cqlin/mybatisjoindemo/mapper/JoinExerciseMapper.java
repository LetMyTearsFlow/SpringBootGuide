package org.cqlin.mybatisjoindemo.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cqlin.mybatisjoindemo.model.Customer;
import org.cqlin.mybatisjoindemo.model.Order;
import org.cqlin.mybatisjoindemo.model.OrderSummary;

@Mapper
public interface JoinExerciseMapper {
    // 第 1 题：两表联合查询，将订单和客户姓名封装成扁平对象。
    List<OrderSummary> findOrderSummaries();

    // 第 2 题：使用 association 将订单所属客户封装成一个对象。
    Order findOrderWithCustomer(@Param("id") Long id);

    // 第 3 题：使用 collection 将客户的多笔订单封装成列表。
    Customer findCustomerWithOrders(@Param("id") Long id);

    // 第 4 题：三表联合查询，订单同时包含客户对象和明细列表。
    List<Order> findAllOrderDetails();
}
