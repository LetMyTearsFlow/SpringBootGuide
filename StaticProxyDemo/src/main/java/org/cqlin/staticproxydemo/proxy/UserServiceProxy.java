package org.cqlin.staticproxydemo.proxy;

import org.cqlin.staticproxydemo.service.UserService;

public class UserServiceProxy implements UserService {

    public UserService target;

    public UserServiceProxy(UserService userService) {
        this.target = userService;
    }
    @Override
    public void addUser(String name) {
        System.out.println("UserServiceProxy.addUser begin");
        target.addUser(name);
        System.out.println("UserServiceProxy.addUser end");
    }
}
