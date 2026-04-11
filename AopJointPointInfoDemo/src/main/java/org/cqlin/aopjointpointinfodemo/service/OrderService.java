package org.cqlin.aopjointpointinfodemo.service;

public interface OrderService {
    String createOrder(String userId, String product);

    String cancelOrder(String userId, Long orderId);

    String queryOrder(Long orderId);
}
