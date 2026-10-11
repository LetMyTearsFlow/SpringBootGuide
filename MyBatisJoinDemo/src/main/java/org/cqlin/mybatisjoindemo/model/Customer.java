package org.cqlin.mybatisjoindemo.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private Long id;
    private String customerName;
    private List<Order> orders = new ArrayList<>();

    // 提供 MyBatis 创建对象所需的无参构造。
    public Customer() {
    }

    // 获取客户主键。
    public Long getId() {
        return id;
    }

    // 设置客户主键。
    public void setId(Long id) {
        this.id = id;
    }

    // 获取客户姓名。
    public String getCustomerName() {
        return customerName;
    }

    // 设置客户姓名。
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // 获取客户订单列表。
    public List<Order> getOrders() {
        return orders;
    }

    // 设置客户订单列表。
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
