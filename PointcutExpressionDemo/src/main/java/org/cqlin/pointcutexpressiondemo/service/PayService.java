package org.cqlin.pointcutexpressiondemo.service;

public interface PayService {
    boolean pay(Long orderId, Double amount);
}
