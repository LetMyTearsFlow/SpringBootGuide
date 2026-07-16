package org.cqlin.marketdemo.dao;

import org.cqlin.marketdemo.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final RowMapper<Order> orderRowMapper = (rs, rowNum) -> {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        order.setBuyerId(rs.getLong("buyer_id"));
        order.setGoodsId(rs.getLong("goods_id"));
        order.setPrice(rs.getBigDecimal("price"));
        order.setStatus(rs.getInt("status"));
        order.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
        return order;
    };

    public void insertOrder(Order order) {
        String sql = "insert into order_info (buyer_id, goods_id, price, status) values(?,?,?,?)";
        jdbcTemplate.update(sql, order.getBuyerId(), order.getGoodsId(), order.getPrice(), order.getStatus());
    }

    public Order findOrderById(Long id) {
        String sql = "select * from order_info where id=?";
        List<Order> orders = jdbcTemplate.query(sql, orderRowMapper, id);
        return orders.get(0);
    }

    public void updateOrder(Order order) {
        Order old = findOrderById(order.getId());
        if (old == null) {
            throw new RuntimeException("订单不存在！");
        }
        if (order.getBuyerId() != null) {
            old.setBuyerId(order.getBuyerId());
        }
        if (order.getStatus() != null) {
            old.setStatus(order.getStatus());
        }
        if (order.getGoodsId() != null) {
            old.setGoodsId(order.getGoodsId());
        }
        if (order.getPrice() != null) {
            old.setPrice(order.getPrice());
        }
        String sql = "update order_info set buyer_id=?, status=?, goods_id=?, price=? where id=?";
        jdbcTemplate.update(sql, old.getBuyerId(), old.getStatus(), old.getGoodsId(), old.getPrice(), old.getId());
    }

    public void deleteOrderById(Long id) {
        String sql = "delete from order_info where id=?";
        jdbcTemplate.update(sql, id);
    }

    public List<Order> findAllOrders() {
        String sql = "select * from order_info";
        return jdbcTemplate.query(sql, orderRowMapper);
    }
}
