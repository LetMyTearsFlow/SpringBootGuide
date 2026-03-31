package org.cqlin.dynamicproxydemo;

import org.cqlin.dynamicproxydemo.handler.OrderServiceHandler;
import org.cqlin.dynamicproxydemo.service.OrderService;
import org.cqlin.dynamicproxydemo.service.OrderServiceImpl;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

class DynamicProxyDemoApplicationTests {

    @Test
    void testDynamicProxyDemo() {
        OrderService orderService = new OrderServiceImpl();
        InvocationHandler ih = new OrderServiceHandler(orderService);
        OrderService orderProxy = (OrderService) Proxy.newProxyInstance(
                orderService.getClass().getClassLoader(),
                orderService.getClass().getInterfaces(),
                ih
        );
        orderProxy.queryOrder("1");
        orderProxy.createOrder("Lin", "apple");
        orderProxy.cancelOrder("admin", "apple");
        try {
            orderProxy.cancelOrder("Lin", "apple");
        } catch (Exception e) {
            System.out.println("捕获异常: " + e.getMessage());
        }

    }

}
