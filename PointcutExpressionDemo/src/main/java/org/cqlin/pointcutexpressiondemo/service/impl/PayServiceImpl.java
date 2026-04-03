package org.cqlin.pointcutexpressiondemo.service.impl;

import org.cqlin.pointcutexpressiondemo.annotation.Audit;
import org.cqlin.pointcutexpressiondemo.service.BaseService;
import org.cqlin.pointcutexpressiondemo.service.PayService;
import org.springframework.stereotype.Service;

@Service("payService")
public class PayServiceImpl extends BaseService implements PayService {

    @Audit
    @Override
    public boolean pay(Long orderId, Double amount) {
        return true;
    }
}
