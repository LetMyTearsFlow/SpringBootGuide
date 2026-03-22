package org.cqlin.propertysourcedemo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Value("${user.name}")
    private String name;

    @Value("${user.age}")
    private int age;

    @Value("${app.name}")
    private String appName;

    public void print() {
        System.out.println("name = " + name);
        System.out.println("age = " + age);
        System.out.println("appName = " + appName);
    }
}
