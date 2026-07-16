package org.cqlin.marketdemo.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Goods {
    public Long id;
    public Long userId;
    public String name;
    public String description;
    public BigDecimal price;
    public Integer status;
    public LocalDateTime createTime;
}
