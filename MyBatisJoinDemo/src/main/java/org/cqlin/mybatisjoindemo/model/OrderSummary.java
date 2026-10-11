package org.cqlin.mybatisjoindemo.model;

public class OrderSummary {
    private Long orderId;
    private String orderNo;
    private String customerName;

    // 提供 MyBatis 创建对象所需的无参构造。
    public OrderSummary() {
    }

    // 获取订单主键。
    public Long getOrderId() {
        return orderId;
    }

    // 设置订单主键。
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    // 获取订单编号。
    public String getOrderNo() {
        return orderNo;
    }

    // 设置订单编号。
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    // 获取客户姓名。
    public String getCustomerName() {
        return customerName;
    }

    // 设置客户姓名。
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
