package org.cqlin.dynamicproxydemo.service;

public interface OrderService {
    String createOrder(String username, String productName);

    boolean cancelOrder(String username, String orderId);

    String queryOrder(String orderId);
}
