package org.cqlin.jdbctemplatedemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Book {
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
    private Integer stock;
    private Long categoryId;
    private Integer status;
    private LocalDateTime createdAt;

    public Book(String title, String author, BigDecimal price, Integer stock, Long categoryId, Integer status) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.stock = stock;
        this.categoryId = categoryId;
        this.status = status;
    }
}
