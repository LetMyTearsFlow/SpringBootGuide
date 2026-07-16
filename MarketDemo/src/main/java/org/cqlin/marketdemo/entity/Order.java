package org.cqlin.marketdemo.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {
    public Long id;
    public Long buyerId;
    public Long goodsId;
    public BigDecimal price;
    public Integer status;
    public LocalDateTime createTime;
}
