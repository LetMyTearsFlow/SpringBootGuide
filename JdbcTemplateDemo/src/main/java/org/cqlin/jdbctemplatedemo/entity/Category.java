package org.cqlin.jdbctemplatedemo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {
    private long id;
    private String name;
    private String description;
    private LocalDateTime createTime;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
