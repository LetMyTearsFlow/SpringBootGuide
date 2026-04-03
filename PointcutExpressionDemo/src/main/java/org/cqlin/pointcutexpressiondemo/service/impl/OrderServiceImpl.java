package org.cqlin.pointcutexpressiondemo.service.impl;

import org.cqlin.pointcutexpressiondemo.annotation.Audit;
import org.cqlin.pointcutexpressiondemo.annotation.SensitiveOp;
import org.cqlin.pointcutexpressiondemo.service.BaseService;
import org.cqlin.pointcutexpressiondemo.service.OrderService;
import org.springframework.stereotype.Service;

@Service("orderService")
@SensitiveOp
public class OrderServiceImpl extends BaseService implements OrderService {

    @Audit
    @Override
    public void createOrder(String user, Long productId) {

    }

    @Override
    public void cancelOrder(String user, Long orderId) {

    }

    @Override
    public String findOrder(Long orderId) {
        return "ok";
    }

    public void internalCall() {
        this.findOrder(1L);
    }
}
