package org.cqlin.mybatisjoindemo.model;

public class OrderItem {
    private Long id;
    private String productName;
    private Integer quantity;

    // 提供 MyBatis 创建对象所需的无参构造。
    public OrderItem() {
    }

    // 获取明细主键。
    public Long getId() {
        return id;
    }

    // 设置明细主键。
    public void setId(Long id) {
        this.id = id;
    }

    // 获取商品名称。
    public String getProductName() {
        return productName;
    }

    // 设置商品名称。
    public void setProductName(String productName) {
        this.productName = productName;
    }

    // 获取商品数量。
    public Integer getQuantity() {
        return quantity;
    }

    // 设置商品数量。
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
