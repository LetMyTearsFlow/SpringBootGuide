package org.cqlin.dynamicproxydemo.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class OrderServiceHandler implements InvocationHandler {
    private final Object target;

    public OrderServiceHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 打印方法名,参数列表
        System.out.println(method.getName() + ": " + Arrays.toString(args));
        // cancelOrder方法权限校验，admin以外抛异常。
        if (method.getName().equals("cancelOrder")) {
            if (args == null || args.length == 0 || !"admin".equals(args[0])) {
                throw new RuntimeException("Only admin user can cancel order");
            }
        }
        // 调用方法
        long start = System.nanoTime();

        Object result = method.invoke(target, args);

        long end = System.nanoTime();
        // 打印返回值
        System.out.println("返回值:" + result);
        // 统计方法耗时。
        System.out.println("this method consumes " + (end - start) + "nanoseconds");
        return result;
    }
}
