package org.cqlin.pointcutexpressiondemo.repository;

import org.springframework.stereotype.Service;

@Service("orderRepository")
public class OrderRepository {
    public void save() {

    }

    public String selectById(long Id) {
        return "data";
    }
}
