package org.cqlin.beanlifecycledemo.bean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class LifeCycleBean implements InitializingBean, DisposableBean {
    private String name;

    // 1. 构造函数
    public LifeCycleBean() {
        System.out.println("1. 构造函数执行");
    }

    // 2. 属性注入
    @Value("jvav")
    public void setName(String name) {
        System.out.println("2. 属性注入 setName");
    }

    // 5. @PostConstruct
    @PostConstruct
    public void postConstruct() {
        System.out.println("5. @PostConstruct");
    }

    // 6. InitializingBean
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("6. InitializingBean.afterPropertiesSet");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("9. @Predestroy");
    }

    // 10. DisposableBean
    @Override
    public void destroy() throws Exception {
        System.out.println("10. DisposableBean.destroy");
    }

    public void initMethod() {
        System.out.println("7. @Bean(initMethod)");
    }

    public void destroyMethod() {
        System.out.println("11. @Destroy(initMethod)");
    }
}
