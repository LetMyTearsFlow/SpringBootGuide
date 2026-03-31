package org.cqlin.dynamicproxydemo.service;

import java.util.concurrent.ThreadLocalRandom;

public class OrderServiceImpl implements OrderService {
    @Override
    public String createOrder(String username, String productName) {
        int orderId = ThreadLocalRandom.current().nextInt(100000);
        String orderIdStr = "ORDER-" + String.format("%05d", orderId);
        System.out.println("正在创建订单...");
        return orderIdStr;
    }

    @Override
    public boolean cancelOrder(String username, String orderId) {
        System.out.println("正在取消订单...");
        return true;
    }

    @Override
    public String queryOrder(String orderId) {
        System.out.println("正在查询订单...");
        return "订单信息: " + orderId;
    }
}
