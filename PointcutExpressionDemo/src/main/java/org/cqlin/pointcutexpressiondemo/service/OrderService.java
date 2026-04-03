package org.cqlin.pointcutexpressiondemo.service;

public interface OrderService {
    void createOrder(String user, Long productId);
    void cancelOrder(String user, Long orderId);
    String findOrder(Long orderId);
}
