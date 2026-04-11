package org.cqlin.aopjointpointinfodemo.service;

import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public String createOrder(String userId, String product) {
        return userId + ": " + product;
    }

    @Override
    public String cancelOrder(String userId, Long orderId) {
        if(!userId.equals("admin")) {
            throw new IllegalArgumentException("only admin can cancel order");
        }
        return "cancel success";
    }

    @Override
    public String queryOrder(Long orderId) {
        return "order info";
    }
}
