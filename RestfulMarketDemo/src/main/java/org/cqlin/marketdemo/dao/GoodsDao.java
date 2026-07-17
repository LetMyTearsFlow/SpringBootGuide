package org.cqlin.marketdemo.dao;

import org.cqlin.marketdemo.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoodsDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final RowMapper<Goods> goodsRowMapper = (rs, rowNum) -> {
        Goods goods = new Goods();
        goods.setId(rs.getLong("id"));
        goods.setUserId(rs.getLong("user_id"));
        goods.setName(rs.getString("name"));
        goods.setDescription(rs.getString("description"));
        goods.setPrice(rs.getBigDecimal("price"));
        goods.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
        return goods;
    };

    public void createGoods(Goods goods) {
        String sql = "insert into goods(user_id, name, description, price, status) values(?,?,?,?,?)";
        jdbcTemplate.update(sql, goods.getUserId(), goods.getName(),
                goods.getDescription(), goods.getPrice(), goods.getStatus());
    }

    public Goods findGoodsById(Long id) {
        String sql = "select * from goods where id=?";
        List<Goods> query = jdbcTemplate.query(sql, goodsRowMapper, id);
        return query.get(0);
    }

    public void updateGoods(Goods goods) {
        Long id = goods.getId();
        Goods old = findGoodsById(id);
        if(goods.getUserId() != null) {
            old.setUserId(goods.getUserId());
        }
        if(goods.getName() != null) {
            old.setName(goods.getName());
        }
        if(goods.getDescription() != null) {
            old.setDescription(goods.getDescription());
        }
        if(goods.getPrice() != null) {
            old.setPrice(goods.getPrice());
        }
        if(goods.getStatus() != null) {
            old.setStatus(goods.getStatus());
        }
        String sql = "update goods set user_id=?, name=?, description=?, " +
                "price=?, status=? where id=?";
        jdbcTemplate.update(sql, old.getUserId(), old.getName(), old.getDescription(),
                old.getPrice(), old.getStatus(), old.getId());
    }
}
