package org.cqlin.mybatisjoindemo.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private String orderNo;
    private Customer customer;
    private List<OrderItem> items = new ArrayList<>();

    // 提供 MyBatis 创建对象所需的无参构造。
    public Order() {
    }

    // 获取订单主键。
    public Long getId() {
        return id;
    }

    // 设置订单主键。
    public void setId(Long id) {
        this.id = id;
    }

    // 获取订单编号。
    public String getOrderNo() {
        return orderNo;
    }

    // 设置订单编号。
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    // 获取所属客户。
    public Customer getCustomer() {
        return customer;
    }

    // 设置所属客户。
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // 获取订单明细列表。
    public List<OrderItem> getItems() {
        return items;
    }

    // 设置订单明细列表。
    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
