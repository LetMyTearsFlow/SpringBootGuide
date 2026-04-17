package org.cqlin.jdbctemplatedemo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Book {
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
    private Integer stock;
    private Long categoryId;
    private Integer status;
    private LocalDateTime createdAt;
}
