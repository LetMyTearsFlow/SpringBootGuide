package org.cqlin.aopjointpointinfodemo;

import org.cqlin.aopjointpointinfodemo.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AopJointPointInfoDemoApplicationTests {

    @Autowired
    public OrderService orderService;
    @Test
    void testOrderAspect() {
        orderService.createOrder("user1", "product1");
        orderService.queryOrder(0L);
        assertThrows(IllegalArgumentException.class,() -> orderService.cancelOrder("dog",1L));
    }

}
