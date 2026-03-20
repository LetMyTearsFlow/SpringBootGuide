package org.cqlin.awaredemo.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class AwareService implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, EnvironmentAware {

    private String beanName;
    private BeanFactory beanFactory;
    private ApplicationContext applicationContext;
    private Environment environment;

    @Override
    public void setBeanName(String name) {
        System.out.println("[Aware回调] BeanNameAware#setBeanName -> " + name);
        this.beanName = name;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("[Aware回调] BeanFactoryAware#setBeanFactory -> " + beanFactory.getClass().getName());
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("[Aware回调] ApplicationContextAware#setApplicationContext -> "
                + applicationContext.getClass().getName());
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        System.out.println("[Aware回调] EnvironmentAware#setEnvironment");
        this.environment = environment;
    }

    /**
     * 该方法标志构造函数执行完毕
     */
    @PostConstruct
    public void init() {
        System.out.println("[初始化方法] @PostConstruct 执行");
    }

    public Map<String, Object> getAwareInfo() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("beanName", beanName);
        result.put("beanFactoryType", beanFactory == null ? null : beanFactory.getClass().getName());
        result.put("applicationContextType", applicationContext == null ? null : applicationContext.getClass().getName());
        result.put("applicationName", environment == null ? null : environment.getProperty("spring.application.name"));
        result.put("demoAuthor", environment == null ? null : environment.getProperty("demo.author"));
        result.put("containsAwareDemoServiceBean", applicationContext != null && applicationContext.containsBean(beanName));
        return result;
    }

    public String getBeanByContext() {
        AwareService bean = applicationContext.getBean(AwareService.class);
        return "通过 ApplicationContext 获取到 Bean: " + bean.getClass().getName();
    }
}
